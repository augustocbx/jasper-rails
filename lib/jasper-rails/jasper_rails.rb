# JasperRails
module JasperRails
  
  def render_jasper_report(opts = {})
    
    unless opts[:xml]
      if opts[:object].respond_to?(:to_xml)
        opts[:xml] = opts[:object].to_xml
        opts[:select_criteria] = "/#{opts[:object].class.to_s.underscore}"
      elsif opts[:collection].respond_to?(:to_xml)
        opts[:xml] = opts[:collection].to_xml
        opts[:select_criteria] = "/#{opts[:collection].first.class.to_s.underscore.pluralize}/#{opts[:collection].first.class.to_s.underscore}"
      else
        if opts[:xml_template]
          opts[:xml] = render_to_string(:template => opts[:xml_template], :layout => false)
        else
          view_paths.each do |path|
            opts[:xml] = render_to_string(:template => "#{params[:controller]}/#{params[:action]}.rxml", :layout => false)
          end
        end
      end
    end
    opts[:disposition] ||= 'inline'
    raise(RuntimeError,"#{params[:action]}.rxml not found in views") unless opts[:xml]
    opts.reverse_merge!({:jasper_file => "#{params[:controller]}/#{params[:action]}", :format => 'pdf'})
    send_data JasperReport.generate(opts), :filename => (opts[:filename] || "report.pdf"), :type => opts[:format], :disposition => opts[:disposition]
  end
  
end

require 'action_controller'
ActionController::Base.send :include, JasperRails