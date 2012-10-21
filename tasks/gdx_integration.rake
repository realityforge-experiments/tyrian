GDX_VERSION='0.9.6'
GDX = struct(
  :core => "com.badlogic.gdx:gdx:jar:#{GDX_VERSION}",
  :core_sources => "com.badlogic.gdx:gdx:jar:sources:#{GDX_VERSION}",
  :tools => "com.badlogic.gdx:gdx-tools:jar:#{GDX_VERSION}",
  :tools_sources => "com.badlogic.gdx:gdx-tools:jar:sources:#{GDX_VERSION}",
  :core_natives => "com.badlogic.gdx:gdx-natives:jar:#{GDX_VERSION}",
  :backend_lwjgl => "com.badlogic.gdx:gdx-backend-lwjgl:jar:#{GDX_VERSION}",
  :backend_lwjgl_sources => "com.badlogic.gdx:gdx-backend-lwjgl:jar:sources:#{GDX_VERSION}",
  :backend_lwjgl_natives => "com.badlogic.gdx:backend_lwjgl_natives:jar:#{GDX_VERSION}",
  :gdx_stb_truetype => "com.badlogic.gdx:gdx-stb-truetype:jar:#{GDX_VERSION}",
  :gdx_stb_truetype_sources => "com.badlogic.gdx:gdx-stb-truetype:jar:sources:#{GDX_VERSION}",
  :gdx_stb_truetype_natives => "com.badlogic.gdx:gdx-stb-truetype-natives:jar:#{GDX_VERSION}"
)

target_dir = "target/libgdx-#{GDX_VERSION}"

task 'unzip_libgdx' do
  download_task = download("libs/libgdx-#{GDX_VERSION}.zip" => "http://libgdx.googlecode.com/files/libgdx-#{GDX_VERSION}.zip")
  download_task.invoke

  unzip(target_dir => download_task.name).extract unless File.exist?(target_dir)
end

artifact(GDX.core).from(file("#{target_dir}/gdx.jar" => %w(unzip_libgdx)))
artifact(GDX.core_sources).from(file("#{target_dir}/sources/gdx-sources.jar" => %w(unzip_libgdx)))
artifact(GDX.core_natives).from(file("#{target_dir}/gdx-natives.jar" => %w(unzip_libgdx)))
artifact(GDX.tools).from(file("#{target_dir}/extensions/gdx-tools.jar" => %w(unzip_libgdx)))
artifact(GDX.tools_sources).from(file("#{target_dir}/extensions/sources/gdx-tools-sources.jar" => %w(unzip_libgdx)))
artifact(GDX.backend_lwjgl).from(file("#{target_dir}/gdx-backend-lwjgl.jar" => %w(unzip_libgdx)))
artifact(GDX.backend_lwjgl_sources).from(file("#{target_dir}/sources/gdx-backend-lwjgl-sources.jar" => %w(unzip_libgdx)))
artifact(GDX.backend_lwjgl_natives).from(file("#{target_dir}/gdx-backend-lwjgl-natives.jar" => %w(unzip_libgdx)))
artifact(GDX.gdx_stb_truetype).from(file("#{target_dir}/extensions/gdx-stb-truetype.jar" => %w(unzip_libgdx)))
artifact(GDX.gdx_stb_truetype_sources).from(file("#{target_dir}/extensions/sources/gdx-stb-truetype-sources.jar" => %w(unzip_libgdx)))
artifact(GDX.gdx_stb_truetype_natives).from(file("#{target_dir}/extensions/gdx-stb-truetype-natives.jar" => %w(unzip_libgdx)))
