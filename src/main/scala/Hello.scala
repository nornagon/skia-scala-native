import scala.scalanative.native._
import scala.scalanative.native.stdio._

@link("skia")
@extern
object skia {
  // sk_types.h
  type sk_imageinfo_t = CStruct4[
    CInt, // width
    CInt, // height
    sk_colortype_t,
    sk_alphatype_t
  ]
  type sk_surfaceprops_t = CStruct2[sk_pixelgeometry_t, sk_surfaceprops_flags_t]
  def sk_colortype_get_default_8888(): sk_colortype_t = extern
  type sk_color_t = UInt
  type sk_surface_t = CStruct0
  type sk_image_t = CStruct0
  type sk_paint_t = CStruct0
  type sk_canvas_t = CStruct0
  type sk_data_t = CStruct0
  type sk_path_t = CStruct0
  type gr_context_t = CStruct0
  type gr_backendobject_t = Ptr[CInt]
  type gr_backendcontext_t = Ptr[CInt]
  type sk_rect_t = CStruct4[CFloat, CFloat, CFloat, CFloat]

  type gr_backend_rendertarget_desc_t = CStruct7[
    CInt,               // fWidth
    CInt,               // fHeight
    gr_pixelconfig_t,   // fConfig
    gr_surfaceorigin_t, // fOrigin
    CInt,               // fSampleCnt
    CInt,               // fStencilBits
    gr_backendobject_t  // fRenderTargetHandle
  ]

  /* enums */
  type sk_colortype_t = UInt
  type sk_alphatype_t = UInt
  type gr_backend_t = UInt
  type gr_pixelconfig_t = UInt
  type gr_surfaceorigin_t = UInt
  type sk_pixelgeometry_t = UInt
  type sk_surfaceprops_flags_t = UInt

  // sk_surface.h
  def sk_surface_new_raster(imageinfo: Ptr[sk_imageinfo_t], props: Ptr[sk_surfaceprops_t]): Ptr[sk_surface_t] = extern
  def sk_surface_new_backend_render_target(context: Ptr[gr_context_t], desc: Ptr[gr_backend_rendertarget_desc_t], props: Ptr[sk_surfaceprops_t]): Ptr[sk_surface_t] = extern
  def sk_surface_get_canvas(surf: Ptr[sk_surface_t]): Ptr[sk_canvas_t] = extern
  def sk_surface_new_image_snapshot(surface: Ptr[sk_surface_t]): Ptr[sk_image_t] = extern
  def sk_surface_new_render_target(context: Ptr[gr_context_t], budgeted: Boolean, info: Ptr[sk_imageinfo_t], sampleCount: CInt, props: Ptr[sk_surfaceprops_t]): Ptr[sk_surface_t] = extern

  // sk_paint.h
  def sk_paint_new(): Ptr[sk_paint_t] = extern
  def sk_paint_delete(paint: Ptr[sk_paint_t]): Unit = extern
  def sk_paint_set_color(paint: Ptr[sk_paint_t], color: sk_color_t): Unit = extern
  def sk_paint_set_antialias(paint: Ptr[sk_paint_t], antialias: CBool): Unit = extern
  def sk_paint_set_style(paint: Ptr[sk_paint_t], style: sk_alphatype_t): Unit = extern
  def sk_paint_set_stroke_width(paint: Ptr[sk_paint_t], width: CFloat): Unit = extern

  // sk_canvas.h
  def sk_canvas_draw_paint(canvas: Ptr[sk_canvas_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_rect(canvas: Ptr[sk_canvas_t], rect: Ptr[sk_rect_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_path(canvas: Ptr[sk_canvas_t], path: Ptr[sk_path_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_draw_oval(canvas: Ptr[sk_canvas_t], rect: Ptr[sk_rect_t], paint: Ptr[sk_paint_t]): Unit = extern
  def sk_canvas_flush(canvas: Ptr[sk_canvas_t]): Unit = extern

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

  // gr_context.h
  def gr_context_create_with_defaults(backend: gr_backend_t, backendContext: Ptr[gr_backendcontext_t]): Ptr[gr_context_t] = extern
  def gr_context_unref(context: Ptr[gr_context_t]): Unit = extern
}
object skia_enums {
  val PREMUL_SK_ALPHATYPE: UInt = 2.toUInt

  val STROKE_SK_PAINT_STYLE: UInt = 1.toUInt

  val OPENGL_GR_BACKEND: UInt = 0.toUInt

  val RGBA_8888_GR_PIXEL_CONFIG: UInt = 5.toUInt

  val TOP_LEFT_GR_SURFACE_ORIGIN: UInt = 1.toUInt
  val BOTTOM_LEFT_GR_SURFACE_ORIGIN: UInt = 2.toUInt
}

@extern
object CGL {
  type CGLContextObj = Ptr[CStruct0]
  type CGLPixelFormatObj = Ptr[CStruct0]

  /* enums */
  type CGLError = UInt
  type CGLPixelFormatAttribute = UInt

  def CGLChoosePixelFormat(attribs: Ptr[CGLPixelFormatAttribute], pix: Ptr[CGLPixelFormatObj], npix: Ptr[CInt]): CGLError = extern
  def CGLCreateContext(pix: CGLPixelFormatObj, share: CGLContextObj, ctx: Ptr[CGLContextObj]): CGLError = extern
  def CGLSetCurrentContext(ctx: CGLContextObj): CGLError = extern

}
object CGL_enums {
  val kCGLPFAOpenGLProfile = 99.toUInt
  val kCGLOGLPVersion_3_2_Core = 0x3200.toUInt
}

@extern
object gl {
  type GLclampf = CFloat
  type GLint = CInt
  type GLsizei = CInt
  type GLbitfield = CUnsignedInt
  type GLenum = CUnsignedInt
  def glClearColor(red: GLclampf, green: GLclampf, blue: GLclampf, alpha: GLclampf): Unit = extern
  def glViewport(x: GLint, y: GLint, width: GLsizei, height: GLsizei): Unit = extern
  def glClear(mask: GLbitfield): Unit = extern
  def glfwPollEvents(): Unit = extern
  def glGetIntegerv(pname: GLenum, params: Ptr[GLint]): Unit = extern
}

object gl_enums {
  val GL_COLOR_BUFFER_BIT = 0x00004000.toUInt
  val GL_FRAMEBUFFER_BINDING = 0x8CA6.toUInt
  val GL_SAMPLES = 0x80A9.toUInt
  val GL_STENCIL_BITS = 0x0D57.toUInt
}

@extern
@link("glfw3")
object glfw3 {
  type GLFWwindow = CStruct0
  type GLFWmonitor = CStruct0

  type GLFWerrorfun = CFunctionPtr2[CInt, CString, Unit]
  type GLFWkeyfun = CFunctionPtr5[Ptr[GLFWwindow], CInt, CInt, CInt, CInt, Unit]

  def glfwInit(): CInt = extern
  def glfwSetErrorCallback(cbfun: GLFWerrorfun): GLFWerrorfun = extern
  def glfwSetKeyCallback(window: Ptr[GLFWwindow], cbfun: GLFWkeyfun): GLFWkeyfun = extern

  def glfwCreateWindow(width: CInt, height: CInt, title: CString, monitor: Ptr[GLFWmonitor], share: Ptr[GLFWwindow]): Ptr[GLFWwindow] = extern
  def glfwWindowHint(hint: CInt, value: CInt): Unit = extern
  def glfwMakeContextCurrent(window: Ptr[GLFWwindow]): Unit = extern
  def glfwWindowShouldClose(window: Ptr[GLFWwindow]): CInt = extern
  def glfwSwapInterval(interval: CInt): Unit = extern
  def glfwGetFramebufferSize(window: Ptr[GLFWwindow], width: Ptr[CInt], height: Ptr[CInt]): Unit = extern
  def glfwSwapBuffers(window: Ptr[GLFWwindow]): Unit = extern
}

object glfw3_enums {
  val GLFW_DOUBLEBUFFER = 0x00021010

  val GLFW_RELEASE = 0
  val GLFW_PRESS = 1
  val GLFW_REPEAT = 2
}

object Hello extends App {
  import skia._
  import skia_enums._
  import glfw3._
  import glfw3_enums._
  import gl._
  import gl_enums._

  glfwInit()
  glfwWindowHint(GLFW_DOUBLEBUFFER, 1)
  val window = glfwCreateWindow(640, 480, c"Yay scala-native", null, null)
  glfwMakeContextCurrent(window)
  glfwSwapInterval(1)

  glfwSetErrorCallback(CFunctionPtr.fromFunction2((error, description) => {
    System.err.println(s"Error $error: ${fromCString(description)}")
  }))

  def keycb(w: Ptr[GLFWwindow], key: CInt, scancode: CInt, action: CInt, mods: CInt): Unit = {
    System.err.println(s"Key event: key=$key scancode=$scancode action=$action mods=$mods")
  }

  glfwSetKeyCallback(window, CFunctionPtr.fromFunction5(keycb))

  val widthP = stackalloc[CInt](1)
  val heightP = stackalloc[CInt](1)
  glfwGetFramebufferSize(window, widthP, heightP)
  val width = !widthP
  val height = !heightP

  val grContext = gr_context_create_with_defaults(OPENGL_GR_BACKEND, null)

  val fbo = stackalloc[CInt](1)
  glGetIntegerv(GL_FRAMEBUFFER_BINDING, fbo)
  val samples = stackalloc[CInt](1)
  val stencilBits = stackalloc[CInt](1)
  glGetIntegerv(GL_SAMPLES, samples)
  glGetIntegerv(GL_STENCIL_BITS, stencilBits)
  val rtDesc = stackalloc[gr_backend_rendertarget_desc_t](1)
  !rtDesc._1 = width
  !rtDesc._2 = height
  !rtDesc._3 = RGBA_8888_GR_PIXEL_CONFIG
  !rtDesc._4 = BOTTOM_LEFT_GR_SURFACE_ORIGIN
  !rtDesc._5 = !samples
  !rtDesc._6 = !stencilBits
  !rtDesc._7 = (!fbo).cast[gr_backendobject_t]

  val surface = sk_surface_new_backend_render_target(grContext, rtDesc, null)
  val canvas = sk_surface_get_canvas(surface)

  val paint = sk_paint_new()

  while (glfwWindowShouldClose(window) == 0) {
    glViewport(0, 0, width, height)
    glClearColor(1, 0, 0, 1)
    glClear(GL_COLOR_BUFFER_BIT)

    sk_paint_set_color(paint, 0xff0000ff.toUInt)
    sk_canvas_draw_paint(canvas, paint)
    sk_paint_set_color(paint, 0xff00ffff.toUInt)
    val rect = stackalloc[sk_rect_t](1)
    !rect._1 = 100.0f
    !rect._2 = 100.0f
    !rect._3 = 540.0f
    !rect._4 = 380.0f
    sk_canvas_draw_rect(canvas, rect, paint)

    sk_canvas_flush(canvas)
    glfwSwapBuffers(window)
    glfwPollEvents()
  }
}

object Windowless {
  import skia._
  import skia_enums._
  import CGL._
  import CGL_enums._
  def example(): Unit = {
    val attribs = stackalloc[CGL.CGLPixelFormatAttribute](3)
    attribs(0) = CGL_enums.kCGLPFAOpenGLProfile
    attribs(1) = CGL_enums.kCGLOGLPVersion_3_2_Core
    attribs(2) = 0.toUInt
    val pix = stackalloc[CGL.CGLPixelFormatObj](1)
    val npix = stackalloc[CInt](1)
    CGL.CGLChoosePixelFormat(attribs, pix, npix)
    val ctx = stackalloc[CGL.CGLContextObj](1)
    CGL.CGLCreateContext(!pix, null, ctx)
    CGL.CGLSetCurrentContext(!ctx)

    def makeSurface(w: Int, h: Int): Ptr[sk_surface_t] = {
      val imageinfo = stackalloc[sk_imageinfo_t](1)
      !imageinfo._1 = 640
      !imageinfo._2 = 480
      !imageinfo._3 = sk_colortype_get_default_8888()
      !imageinfo._4 = skia_enums.PREMUL_SK_ALPHATYPE
      sk_surface_new_raster(imageinfo, null)
    }

    def makeGpuBackedSurface(w: Int, h: Int): Ptr[sk_surface_t] = {
      val context = gr_context_create_with_defaults(skia_enums.OPENGL_GR_BACKEND, null)
      val imageinfo = stackalloc[sk_imageinfo_t](1)
      !imageinfo._1 = 640
      !imageinfo._2 = 480
      !imageinfo._3 = sk_colortype_get_default_8888()
      !imageinfo._4 = skia_enums.PREMUL_SK_ALPHATYPE
      
      val surface = sk_surface_new_render_target(context, false, imageinfo, 0, null)
      gr_context_unref(context)

      surface
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

    val surface = makeGpuBackedSurface(640, 480)
    val canvas = sk_surface_get_canvas(surface)

    draw(canvas)
    emitPng("test.png", surface)
  }
}
