import java.lang.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;

public class MainScrapperChakri {
    ArrayList jobLinks = new ArrayList();
    ArrayList jobTitle = new ArrayList();
    String title,jobSalary,jobRequirements,link,jobResponsibilities,LastDate,vacancy;
    public void scrapperLink(String start){
        try{
            Document doc = Jsoup.connect(start).userAgent("Mozilla/56.0.0").get();
            Elements links = doc.select("body > div.container-fluid.content_full_bg > div > div > form > div > div > div.col-xs-12.col-sm-8.col-md-9.category_dtls_right_container > div > div.row > div.col-xs-9.col-sm-8.col-md-8 > h3 > a");
            for (Element link : links) {
                jobTitle.add(link.text());
                jobLinks.add(link.attr("abs:href"));
            }
            Elements next = doc.select("body > div.container-fluid.content_full_bg > div > div > form > div > div > div.col-xs-12.col-sm-8.col-md-9.category_dtls_right_container > div.col-xs-12.col-sm-5.col-md-12.pagination_container > div > div > div > ul > li:nth-child(4) > a");
            Elements disable = doc.select("body > div.container-fluid.content_full_bg > div > div > form > div > div > div.col-xs-12.col-sm-8.col-md-9.category_dtls_right_container > div.col-xs-12.col-sm-5.col-md-12.pagination_container > div > div > div > ul > li:nth-child(4)");
            //System.out.println(disable.hasClass("disabled"));
            if(next != null && !disable.hasClass("disabled")){
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
                Document doc = Jsoup.connect(jobLinks.get(i).toString()).userAgent("Mozilla/56.0.0").get();
                title = jobTitle.get(i).toString();
                link = jobLinks.get(i).toString();
                Elements document = doc.select("body > div.container-fluid.content_full_bg > div > div > div.row > div.col-xs-12.col-sm-8.col-md-8.no_right_padding.print_container");
                jobResponsibilities = document.select("h3:containsOwn(Job Description) ~ ul:first-of-type").toString().replaceAll("<li>", "*)").replaceAll("</li>",".").replaceAll("<ul>", "").replaceAll("</ul>","").trim();
                jobRequirements = document.select("h3:containsOwn(Job Description) ~ ul:last-of-type").toString().replaceAll("<li>", "*)").replaceAll("</li>",".").replaceAll("<ul>", "").replaceAll("</ul>","").trim();
                jobSalary = doc.select("body > div.container-fluid.content_full_bg > div > div > div.row > div.col-xs-12.col-sm-8.col-md-8.no_right_padding.print_container > div > div > div.col-xs-12.col-sm-9.col-md-9.dtls_salary_right_p > p").text();
                LastDate = doc.select("body > div.container-fluid.content_full_bg > div > div > div.row > div.col-xs-12.col-sm-8.col-md-8.no_right_padding.print_container > div > div.col-xs-12.col-sm-12.col-md-12.dtls_save_full_container > div > div:nth-child(4) > p > span").text();                
                insertIntoDB();
            }catch(Exception e){
                System.out.println("Parsing Error" + e.getMessage());
            }
        }
    }

public String getTitle()
{
    return title;
}
public String getLink()
{
    return link;
}
public String getJobResponsibilities()
{
    return jobResponsibilities;
}
public String getJobRequirements()
{
    return jobRequirements;
}
public String getjobSalary()
{
    return jobSalary;
}
public String getLastDate()
{
    return LastDate;
}
public String getVacancy()
{
    return vacancy;
}


    public void insertIntoDB()
    {
        String query = "INSERT INTO description VALUES ('"+getTitle()+"','"+getLink()+"','"+getjobSalary()+"','"+getJobRequirements()+"','"+getJobResponsibilities()+"','"+getVacancy()+"','"+getLastDate()+"');";
        
        //System.out.println(query);
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder", "root", "");
            Statement stm = con.createStatement();
            stm.execute(query);
            stm.close();
            con.close();
                    
        }
        catch(Exception ex)
        {
            System.out.println("Exception : " +ex.getMessage());
        }
    }
}


