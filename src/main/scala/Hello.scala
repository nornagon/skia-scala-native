import scala.scalanative.native._
import scala.scalanative.native.stdio._

@link("skia")
@extern
object skia {
  // sk_types.h
  type sk_colortype_t = CUnsignedInt
  type sk_alphatype_t = CUnsignedInt
  type sk_imageinfo_t = CStruct4[CInt, CInt, sk_colortype_t, sk_alphatype_t]
  type sk_surfaceprops_t = CStruct2[UInt, UInt]
  def sk_colortype_get_default_8888(): sk_colortype_t = extern
  type sk_color_t = UInt
  type sk_surface_t = CStruct0
  type sk_image_t = CStruct0
  type sk_paint_t = CStruct0
  type sk_canvas_t = CStruct0
  type sk_data_t = CStruct0
  type sk_path_t = CStruct0
  type sk_rect_t = CStruct4[CFloat, CFloat, CFloat, CFloat]

  // sk_surface.h
  def sk_surface_new_raster(imageinfo: Ptr[sk_imageinfo_t], props: Ptr[sk_surfaceprops_t]): Ptr[sk_surface_t] = extern
  def sk_surface_get_canvas(surf: Ptr[sk_surface_t]): Ptr[sk_canvas_t] = extern
  def sk_surface_new_image_snapshot(surface: Ptr[sk_surface_t]): Ptr[sk_image_t] = extern

  // sk_paint.h
  def sk_paint_new(): Ptr[sk_paint_t] = extern
  def sk_paint_delete(paint: Ptr[sk_paint_t]): Unit = extern
  def sk_paint_set_color(paint: Ptr[sk_paint_t], color: sk_color_t): Unit = extern
  def sk_paint_set_antialias(paint: Ptr[sk_paint_t], antialias: CBool): Unit = extern
  def sk_paint_set_style(paint: Ptr[sk_paint_t], style: UInt): Unit = extern
  def sk_paint_set_stroke_width(paint: Ptr[sk_paint_t], width: CFloat): Unit = extern

  // sk_canvas.h
  def sk_canvas_draw_paint(canvas: Ptr[sk_canvas_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_rect(canvas: Ptr[sk_canvas_t], rect: Ptr[sk_rect_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_path(canvas: Ptr[sk_canvas_t], path: Ptr[sk_path_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_oval(canvas: Ptr[sk_canvas_t], rect: Ptr[sk_rect_t], paint: Ptr[sk_paint_t]): Unit = extern

  // sk_image.h
  def sk_image_encode(image: Ptr[sk_image_t]): Ptr[sk_data_t] = extern
  def sk_image_unref(image: Ptr[sk_image_t]): Unit = extern

  // sk_data.h
  def sk_data_unref(data: Ptr[sk_data_t]): Unit = extern
  def sk_data_get_data(data: Ptr[sk_data_t]): Ptr[Byte] = extern
  def sk_data_get_size(data: Ptr[sk_data_t]): CSize = extern

  // sk_path.h
  def sk_path_new(): Ptr[sk_path_t] = extern
  def sk_path_delete(path: Ptr[sk_path_t]): Unit = extern
  def sk_path_move_to(path: Ptr[sk_path_t], x: CFloat, y: CFloat): Unit = extern
  def sk_path_line_to(path: Ptr[sk_path_t], x: CFloat, y: CFloat): Unit = extern
  def sk_path_cubic_to(path: Ptr[sk_path_t], x0: CFloat, y0: CFloat, x1: CFloat, y1: CFloat, x2: CFloat, y2: CFloat): Unit = extern
}
object skia_enums {
  val PREMUL_SK_ALPHATYPE: UInt = 2.toUInt
  val STROKE_SK_PAINT_STYLE: UInt = 1.toUInt
}

object Hello extends App {
  import skia._

  def makeSurface(w: Int, h: Int): Ptr[sk_surface_t] = {
    val imageinfo = stackalloc[sk_imageinfo_t](1)
    !imageinfo._1 = 640
    !imageinfo._2 = 480
    !imageinfo._3 = sk_colortype_get_default_8888()
    !imageinfo._4 = skia_enums.PREMUL_SK_ALPHATYPE
    sk_surface_new_raster(imageinfo, null)
  }

  def draw(canvas: Ptr[sk_canvas_t]) = {
    val fill = sk_paint_new()
    sk_paint_set_color(fill, 0xff0000ff.toUInt)
    sk_canvas_draw_paint(canvas, fill)

    sk_paint_set_color(fill, 0xff00ffff.toUInt)
    val rect = stackalloc[sk_rect_t](1)
    !rect._1 = 100.0f
    !rect._2 = 100.0f
    !rect._3 = 540.0f
    !rect._4 = 380.0f
    sk_canvas_draw_rect(canvas, rect, fill)

    val stroke = sk_paint_new()
    sk_paint_set_color(stroke, 0xffff0000.toUInt)
    sk_paint_set_antialias(stroke, true)
    sk_paint_set_style(stroke, skia_enums.STROKE_SK_PAINT_STYLE)
    sk_paint_set_stroke_width(stroke, 5.0f)

    val path = sk_path_new()
    sk_path_move_to(path, 50.0f, 50.0f)
    sk_path_line_to(path, 590.0f, 50.0f)
    sk_path_cubic_to(path, -490.0f, 50.0f, 1130.0f, 430.0f, 50.0f, 430.0f)
    sk_path_line_to(path, 590.0f, 430.0f)
    sk_canvas_draw_path(canvas, path, stroke)

    sk_paint_set_color(fill, 0x8000FF00.toUInt)
    val rect2 = stackalloc[sk_rect_t](1);
    !rect2._1 = 120.0f;
    !rect2._2 = 120.0f;
    !rect2._3 = 520.0f;
    !rect2._4 = 360.0f;
    sk_canvas_draw_oval(canvas, rect2, fill);

    sk_paint_delete(stroke)
    sk_paint_delete(fill)
  }

  def emitPng(path: String, surface: Ptr[sk_surface_t]) = Zone { implicit z =>
    val image = sk_surface_new_image_snapshot(surface)
    val data = sk_image_encode(image)
    sk_image_unref(image)
    val f = fopen(toCString(path), c"wb")
    fwrite(sk_data_get_data(data), sk_data_get_size(data), 1, f)
    fclose(f)
    sk_data_unref(data)
  }


  val surface = makeSurface(640, 480)
  val canvas = sk_surface_get_canvas(surface)

  draw(canvas)
  emitPng("test.png", surface)
}
