package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;
import phonebase.android.kizema.phonebasetestapp.uicontrol.SortController;

public class ContactHelper {

    public static Contact create(String phone, int price, String owner){
        Contact c = new Contact();

        c.phoneNumber = phone;
        c.phoneNumberPrice = price;
        c.phoneNumberOwner = owner;

        return c;
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
                contactlist = queryBuilder.list();
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
                                ContactDao.Properties.PhoneNumberPrice.like(pattern))
                        .orderAsc(ContactDao.Properties.PhoneNumberPrice)
                        .list();
                break;
            case SORT_DOWN:
                contactlist = queryBuilder
                        .whereOr(ContactDao.Properties.PhoneNumber.like(pattern),
                                ContactDao.Properties.PhoneNumberPrice.like(pattern),
                                ContactDao.Properties.PhoneNumberOwner.like(pattern))
                        .orderDesc(ContactDao.Properties.PhoneNumberPrice)
                        .list();
                break;
            default:
                contactlist = queryBuilder
                        .whereOr(ContactDao.Properties.PhoneNumber.like(pattern),
                                ContactDao.Properties.PhoneNumberPrice.like(pattern),
                                ContactDao.Properties.PhoneNumberOwner.like(pattern))
                        .list();
                break;
        }

        return contactlist;
    }
}
