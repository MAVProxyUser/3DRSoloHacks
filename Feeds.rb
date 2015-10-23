require 'rubygems'
require 'json'


beta = %x[curl -H "Authorization: Token bd02efe84c11b41d11c4644e9b04ad4673deb6df" https://firmwarehouse.3dr.com/products/?channel=Beta 2>/dev/null]
prod = %x[curl -H "Authorization: Token bd02efe84c11b41d11c4644e9b04ad4673deb6df" https://firmwarehouse.3dr.com/products/?channel=Production 2>/dev/null]

def print_relnotes(info)
	parsed = JSON.parse(info)

	parsed["results"].each do |result|
	  releases = result["releases"].each do |release|
		  unless release.nil?
		    puts release["release_notes"]
		    puts release["file"]
		  else
		#    puts "There is no release!"
		  end
	  end
	end
end

if ARGV.length < 1
 puts "ruby #{ARGV[0]} [prod|beta|both]"
end 

if ARGV[0] == "prod"
  print_relnotes(prod)
elsif ARGV[0] == "beta"
  print_relnotes(beta)
elsif ARGV[0] == "both"
  print_relnotes(beta)
  print_relnotes(prod)
end

