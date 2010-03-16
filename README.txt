JasperRails
===========
JasperRails is a Ruby gem to simplify generating Jasper Reports on the fly
from within a Ruby on Rails application. 

Requirements
============
Before generating a report, you'll need a properly built and compiled .jasper file from
iReport or another JasperReport building tool. 
Instructions for generating this file is beyond the scope of this plugin. 

Thanks
======
Enormous Thanks to the following people. Without their contributions,
this would not have been possible.
* Herman Jansen - for his detailed update to the rails wiki on generating jasper reports,
  and for his additional support on the Rails mailing list.
* Jonas Schwertfeger for his original jasperreports_on_rails tarball containing the
  XmlJasperInterface java library which greatly helped in the creation of this plugin.
* All the anonymous developers who posted and updated the original instructions to the
  rails wiki.
* The JasperReports development team!

Example
=======

	#Assumes you have a compiled index.jasper file in /app/views/people
  class PeopleController << ApplicationController
  	def index
  	  @people = Person.find(:all)
  	  respond_to do |format|
  	    format.xml => { render :text => @people.to_xml }
  	    format.pdf => { render_jasper_report :collection => @people }
  	    format.html #default
  	  end
    end
  end
  
  http://mydomain.com/people       #=> html
  http://mydomain.com/people.xml   #xml file useful for building the report in iReport
  http://mydomian.com/people.pdf   #final jasper report with data 

	
Copyright (c) 2008 World Wide, IDEA, INC, released under the MIT license

The included jasperreports java libraries are seperately licensed under
the LGPL. Please see the jasperreports folder for additional information.