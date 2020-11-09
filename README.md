# EchoWebhook
 A simple way to analyze the structure of a poorly documented webhook.

 EchoWebhook is a small Java Servlet which saves any requests comeing in at /echo and can repeat them by calling a GET reqest on /request. It was made for situations where an unknown webhook can send data to a custom URL, however you need to know what Parameters and Body looks like, to implement it in your Backend (For Example ASP.NET, Spring Boot, ...). To clear the request history simply call /reset.

 # Setup
 To setup EchoWebhook you need a Webserver which can run Java Servlets (for example Apache-Tomcat). The easiest way to install the .war file is by using the Tomcat Manager in your browser.

# Usage
- /echo - Catch request and save to file
- /requests - Get all cought requests
- /reset - Clear file of cought requests