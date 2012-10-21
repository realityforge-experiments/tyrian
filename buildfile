desc "Tyrian: The space game tutorial"
define('tyrian') do
  project.group = 'com.blogspot.steigert.tyrian'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  compile.with GDX.core,
               GDX.core_natives,
               GDX.backend_lwjgl,
               GDX.backend_lwjgl_natives,
               GDX.tools

  package(:jar)
  package(:sources)
end
