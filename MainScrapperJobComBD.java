import java.lang.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;

public class MainScrapperJobComBD {
    ArrayList jobLinks = new ArrayList();
    ArrayList jobTitle = new ArrayList();
    String title,link,jobResponsibilities,jobRequirements,jobVacancies,jobSalary,jobDeadline;
    public void scrapperLink(String start){
        try{
            Document doc = Jsoup.connect(start).userAgent("Mozilla/56.0.0").get();
            Elements links = doc.select("body > table:nth-child(3) > tbody > tr > td > table:nth-child(3) > tbody > tr > td:nth-child(3) > a");
            
            for (Element link : links) {
                jobTitle.add(link.text());
                jobLinks.add(link.attr("abs:href"));            
            }
            Elements next = doc.select("body > table:nth-child(3) > tbody > tr > td > table:nth-child(5) > tbody > tr > td > a:last-of-type");
            String next_check = next.select("strong > font > img").toString();
            if(!next_check.isEmpty()){
                String nextButton = next.attr("abs:href");
                scrapperLink(nextButton);
            }         
        }catch(Exception e){
            System.out.println("Parsing Error" + e.getMessage());
        }
        
    }

    public void scrapperPost(){
        for(int i =0;i<jobLinks.size();i++){
            try{
                Connection con=null;
                Statement st = null;
                ResultSet rs = null;
                
                Document doc = Jsoup.connect(jobLinks.get(i).toString()).userAgent("Mozilla/56.0.0").get();
                title = jobTitle.get(i).toString();
                
                link = jobLinks.get(i).toString();

                Elements document = doc.select("#container_td > table:nth-child(2) > tbody > tr:nth-child(2) > td > table > tbody");
                
                jobResponsibilities = document.select("td:containsOwn(Responsibilities) ~ td:last-of-type").toString().replaceAll("<li>", "*)").replaceAll("</li>","\n").replaceAll("<td>", "").replaceAll("</td>","").trim();
                
                String jobRequirements1 = document.select("td:containsOwn(Educational Requirement) ~ td:last-of-type").text();
                String jobRequirements2 = document.select("td:containsOwn(Experience) ~ td:last-of-type").text();
                jobRequirements = "*)"+jobRequirements1+"\n"+"*)"+jobRequirements2;
                
                jobVacancies = document.select("td:containsOwn(Number of Position) ~ td:last-of-type").text();

                jobSalary = document.select("td:containsOwn(Salary) ~ td:last-of-type").text();

                jobDeadline = doc.select("#container_td > table:nth-child(2) > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(2)").text().replaceAll("Application Deadline:","").trim();

                String query = "INSERT INTO description VALUES ('"+title+"','"+link+"','"+jobSalary+"','"+jobRequirements+"','"+jobResponsibilities+"','"+jobVacancies+"','"+jobDeadline+"');";
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
                st = con.createStatement();
                int rs1 = st.executeUpdate(query);

            }catch(Exception e){
                System.out.println("Parsing Error" + e.getMessage());
            }
        }
    }
}
