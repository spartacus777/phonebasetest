package phonebase.android.kizema.phonebasetestapp.control;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import phonebase.android.kizema.phonebasetestapp.model.Contact;
import phonebase.android.kizema.phonebasetestapp.model.ContactHelper;

public class JsonHelper {

    private static JsonHelper instance;

    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String PHONE_NUMBER_PRICE = "phoneNumberPrice";
    public static final String PHONE_NUMBER_OWNER = "phoneNumberOwner";


    public static synchronized JsonHelper getInstance() {
        if (instance == null) {
            instance = new JsonHelper();
        }

        return instance;
    }

    private JsonHelper() {}

    public List<Contact> parse(String bussJson) {
        try {
            JSONArray obj = new JSONArray(bussJson);
            List<Contact> busModels = new ArrayList<>();

            try {

                for (int j = 0; j < obj.length(); ++j) {
                    String number = obj.getJSONObject(j).getString(PHONE_NUMBER);
                    String priceStr = obj.getJSONObject(j).getString(PHONE_NUMBER_PRICE);
                    String owner = obj.getJSONObject(j).getString(PHONE_NUMBER_OWNER);

                    int price = Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));

                    Contact contact = ContactHelper.create(number, price, owner);
                    busModels.add(contact);
                }
            } catch (JSONException ex) {
                //probably broken entry, ignore it
            }

            return busModels;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
