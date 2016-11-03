package search;

public class UrlInfo {
	private String url = "";
	private String text = "";
	
	public UrlInfo() {
	}
	
	public void setUrl(String indexUrl)
	{
		this.url = indexUrl;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setText(String indexText)
	{
		this.text = indexText;
	}
	
	public String getText()
	{
		return text;
	}
	
	public boolean isValid()
	{
		if (url.isEmpty())
			return false;
		
		else
			return true;
	}
}
