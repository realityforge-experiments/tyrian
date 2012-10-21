GDX_VERSION='nightly-20121021'
GDX = struct(
  :core => "com.badlogic.gdx:gdx:jar:#{GDX_VERSION}",
  :core_sources => "com.badlogic.gdx:gdx:jar:sources:#{GDX_VERSION}",
  :tools => "com.badlogic.gdx:gdx-tools:jar:#{GDX_VERSION}",
  :tools_sources => "com.badlogic.gdx:gdx-tools:jar:sources:#{GDX_VERSION}",
  :core_natives => "com.badlogic.gdx:gdx-natives:jar:#{GDX_VERSION}",
  :backend_lwjgl => "com.badlogic.gdx:gdx-backend-lwjgl:jar:#{GDX_VERSION}",
  :backend_lwjgl_sources => "com.badlogic.gdx:gdx-backend-lwjgl:jar:sources:#{GDX_VERSION}",
  :backend_lwjgl_natives => "com.badlogic.gdx:backend_lwjgl_natives:jar:#{GDX_VERSION}",
  # Package does not include sources for gdx_setup_ui
  :gdx_setup_ui => "com.badlogic.gdx:gdx-setup-ui:jar:#{GDX_VERSION}"
)

target_dir = "target/libgdx-#{GDX_VERSION}"

task 'unzip_libgdx' do
  download_task = download("downloads/libgdx-#{GDX_VERSION}.zip" => "http://libgdx.googlecode.com/files/libgdx-#{GDX_VERSION}.zip")
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
artifact(GDX.gdx_setup_ui).from(file("#{target_dir}/gdx-setup-ui.jar" => %w(unzip_libgdx)))
