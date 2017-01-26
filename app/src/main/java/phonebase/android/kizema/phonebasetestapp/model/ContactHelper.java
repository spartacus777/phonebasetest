package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;
import phonebase.android.kizema.phonebasetestapp.uicontrol.SortController;

public class ContactHelper {

    public static Contact create(String phone, int price, String owner){
        Contact c = getContactByOwner(owner);
        boolean isNew = false;

        if (c == null){
            isNew = true;
            c = new Contact();
        }

        c.phoneNumber = phone;
        c.phoneNumberPrice = price;
        c.phoneNumberOwner = owner;

        if (isNew) {
            App.getDaoSession().getContactDao().insert(c);
        } else {
            App.getDaoSession().getContactDao().update(c);
        }

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
}
