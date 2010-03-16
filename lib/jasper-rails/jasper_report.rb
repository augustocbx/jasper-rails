class JasperReport
  include Config
  
  class << self
    
    def generate(opts = {}) #jasper_file, format, select_criteria)
      opts[:jasper_file] << '.jasper' if !opts[:jasper_file].match(/\.jasper$/)
      interface_classpath=File.dirname(__FILE__)+"/../../includes/xml_jasper_interface/" 
      #case CONFIG['host']
      #  when /mswin32/
      #    Dir.foreach(RAILS_ROOT+"/vendor/plugins/jasper_rails/jasperreports/lib") do |file|
      #      interface_classpath << ";#{RAILS_ROOT}/vendor/plugins/jasper_rails/jasperreports/lib/"+file if (file != '.' and file != '..' and file.match(/.jar/))
      #    end
      #  else
          Dir.foreach(File.dirname(__FILE__)+"/../../includes/jasperreports/lib") do |file|
            interface_classpath << ":#{File.dirname(__FILE__)}/../../includes/jasperreports/lib/"+file if (file != '.' and file != '..' and file.match(/.jar/))
          end
      #end
      
      result=nil
      IO.popen "java -Djava.awt.headless=true -cp \"#{interface_classpath}\" XmlJasperInterface -o#{opts[:format] || 'pdf'} -f#{RAILS_ROOT}/app/views/#{opts[:jasper_file]} -x#{opts[:select_criteria]}", "w+" do |pipe|
        pipe.write opts[:xml]
        pipe.close_write
        result = pipe.read
        pipe.close
      end
      return result
    end
    
  end
end