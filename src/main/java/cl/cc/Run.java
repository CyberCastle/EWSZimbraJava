package cl.cc;

import java.net.URI;
import java.net.URISyntaxException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

/**
 *
 * @author CyberCastle
 */
public class Run {

    public static void main(String... arg) throws Exception {

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

        try {
            service.setUrl(new URI("https://mail.zboxapp.com/EWS/Exchange.asmx"));
        } catch (URISyntaxException ex) {
            ex.printStackTrace(System.err);
            return;
        }

        ExchangeCredentials credentials = new WebCredentials("user", "password");
        service.setCredentials(credentials);

        // Bind to the Inbox.
        Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);

        
        System.out.println(folder.getTotalCount());
        
        
        // Aquí es donde se cae, puesto que el servidor entrega el soap incompleto
        FindItemsResults<Item> findResults = folder.findItems(new ItemView(10));
        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
        
    }

    
            //MOOOOOOST IMPORTANT: load messages' properties before
        /*service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);

        for (Item item : findResults.getItems()) {
            // Do something with the item as shown
            System.out.println("id==========" + item.getId());
            System.out.println("sub==========" + item.getSubject());
        }*/
    
    
   /* public void findItems() {
	ItemView view = new ItemView(10);
	view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);
	view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject, ItemSchema.DateTimeReceived));

	FindItemsResults<Item> findResults =
    	service.findItems(WellKnownFolderName.Inbox,
        	new SearchFilter.SearchFilterCollection(
				LogicalOperator.Or, new SearchFilter.ContainsSubstring(ItemSchema.Subject, "EWS"),
			new SearchFilter.ContainsSubstring(ItemSchema.Subject, "API")), view);

    //MOOOOOOST IMPORTANT: load items properties, before
    service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
	System.out.println("Total number of items found: " + findResults.getTotalCount());

	for (Item item : findResults) {
		System.out.println(item.getSubject());
		System.out.println(item.getBody());
		// Do something with the item.
	}
}*/
}
