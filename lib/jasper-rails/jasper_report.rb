class JasperReport
  include Config

  class << self

    def generate(opts = {}) #jasper_file, format, select_criteria)
      if RUBY_PLATFORM.include?('i386-mingw32')
        sep = ';'
      else
        sep = ':'
      end
      interface_classpath=File.dirname(__FILE__)+"/../../includes/xml_jasper_interface/"
      opts[:jasper_file] << '.jasper' if !opts[:jasper_file].match(/\.jasper$/)

      Dir.foreach(File.dirname(__FILE__)+"/../../includes/jasperreports/lib") do |file|
        interface_classpath << "#{sep}#{File.dirname(__FILE__)}/../../includes/jasperreports/lib/"+file if (file != '.' and file != '..' and file.match(/.jar/))
      end

      Dir.foreach(File.dirname(__FILE__)+"/../../includes/jasperreports/dist") do |file|
        interface_classpath << "#{sep}#{File.dirname(__FILE__)}/../../includes/jasperreports/dist/"+file if (file != '.' and file != '..' and file.match(/.jar/))
      end

      result=nil
      IO.popen "java -Djava.awt.headless=true -cp \"#{interface_classpath}\" XmlJasperInterface -o#{opts[:format] || 'pdf'} -f#{RAILS_ROOT}/app/views/#{opts[:jasper_file]} -x#{opts[:select_criteria]}", "w+b" do |pipe|
        pipe.write opts[:xml]
        pipe.close_write
        result = pipe.read
        pipe.close
      end
      return result
    end

  end
end