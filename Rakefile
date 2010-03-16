require 'config/requirements'
require 'config/hoe' # setup Hoe + all gem configuration

Dir['tasks/**/*.rake'].each { |rake| load rake }

task :build_manifest do
  #manifest = list_files
  #puts manifest.inspect
  #puts manifest.size
  manifest = File.open('Manifest.txt','w')
  manifest.puts list_files
  manifest.close
end

def list_files(directory = nil)
  files = []
  Dir.foreach(directory || '.') do |file|
    next if ['.','..','.svn','.project','.loadpath'].include?(file)
    path_to_file = (directory ? (directory + '/') : '') + file
    files += File.directory?(path_to_file) ? (list_files(path_to_file) || []) : [path_to_file]
  end
  return files
end