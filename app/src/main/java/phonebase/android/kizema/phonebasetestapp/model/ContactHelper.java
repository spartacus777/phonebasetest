package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;
import phonebase.android.kizema.phonebasetestapp.uicontrol.SortController;

public class ContactHelper {

    public static Contact create(String phone, int price, String owner, String dictionaryWord){
        Contact c = new Contact();

        c.phoneNumber = phone;
        c.phoneNumberPrice = price;
        c.phoneNumberOwner = owner;
        c.dictionaryWord = dictionaryWord;

        c.sortValue = getContactSortValue(c);

        return c;
    }

    public static double getContactSortValue(Contact contact){
        if (contact.dictionaryWord == null || contact.dictionaryWord.length() == 0){
            return 1000 / contact.getPhoneNumberPrice();
        } else {
            return 100000 * contact.dictionaryWord.length() / contact.getPhoneNumberPrice();
        }
    }

    public static Contact getContactByOwner(String contactOwner){
        DaoSession daoSession = App.getDaoSession();
        ContactDao confDao = daoSession.getContactDao();

        QueryBuilder<Contact> queryBuilder = confDao.queryBuilder();
        List<Contact> contactlist = queryBuilder.where(ContactDao.Properties.PhoneNumberOwner.eq(contactOwner)).list();

        if (contactlist == null || contactlist.size() == 0) {
            return null;
        } else {
            return contactlist.get(0);
        }
    }

    public static List<Contact> getAll(SortController.Status status){
        DaoSession daoSession = App.getDaoSession();
        ContactDao confDao = daoSession.getContactDao();

        QueryBuilder<Contact> queryBuilder = confDao.queryBuilder();
        List<Contact> contactlist;

        switch (status){
            case SORT_UP:
                contactlist = queryBuilder.orderAsc(ContactDao.Properties.PhoneNumberPrice).list();
                break;
            case SORT_DOWN:
                contactlist = queryBuilder.orderDesc(ContactDao.Properties.PhoneNumberPrice).list();
                break;
            default:
                contactlist = queryBuilder.orderDesc(ContactDao.Properties.SortValue).list();
                break;
        }

        return contactlist;
    }

    public static List<Contact> getAllSearch(SortController.Status status, String searchWord){
        DaoSession daoSession = App.getDaoSession();
        ContactDao confDao = daoSession.getContactDao();

        QueryBuilder<Contact> queryBuilder = confDao.queryBuilder();
        List<Contact> contactlist;

        String pattern = "%"+searchWord+"%";

        switch (status){
            case SORT_UP:
                contactlist = queryBuilder
                        .whereOr(ContactDao.Properties.PhoneNumber.like(pattern),
                                ContactDao.Properties.PhoneNumberOwner.like(pattern),
                                ContactDao.Properties.PhoneNumberPrice.like(pattern),
                                ContactDao.Properties.DictionaryWord.like(pattern))
                        .orderAsc(ContactDao.Properties.PhoneNumberPrice)
                        .list();
                break;
            case SORT_DOWN:
                contactlist = queryBuilder
                        .whereOr(ContactDao.Properties.PhoneNumber.like(pattern),
                                ContactDao.Properties.PhoneNumberPrice.like(pattern),
                                ContactDao.Properties.PhoneNumberOwner.like(pattern),
                                ContactDao.Properties.DictionaryWord.like(pattern))
                        .orderDesc(ContactDao.Properties.PhoneNumberPrice)
                        .list();
                break;
            default:
                contactlist = queryBuilder
                        .whereOr(ContactDao.Properties.PhoneNumber.like(pattern),
                                ContactDao.Properties.PhoneNumberPrice.like(pattern),
                                ContactDao.Properties.PhoneNumberOwner.like(pattern),
                                ContactDao.Properties.DictionaryWord.like(pattern))
                        .orderDesc(ContactDao.Properties.SortValue)
                        .list();
                break;
        }

        return contactlist;
    }
}
