package ru.denfad.studturism.Sevice;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.Model.UserPoint;
import ru.denfad.studturism.Model.Price;
import ru.denfad.studturism.Model.Service;
import ru.denfad.studturism.R;

public class MainService {

    private List<Hostel> hostels = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<UserPoint> userPoints = new ArrayList<>();
    private static MainService instance;

    private MainService() {
        hostels.add(new Hostel(1, "Арбат", "МГУ", "Москва", 1, 3, 1235, R.drawable.hostel_1));
        hostels.add(new Hostel(2,"Общежитие №3", "АГУТ", "Ростов на дону", 1, 11, 600, R.drawable.hostel_2));
        hostels.add(new Hostel(3,"Гостиница университета", "МоСаНИНА", "Можайск", 3, 7, 900, R.drawable.hostel_3));
        hostels.add(new Hostel(4,"Общежитие центральное", "УДН", "Новосибирск", 1, 5, 78, R.drawable.hostel_4));
        books.add(new Book("23 Февраля 2023", "24 Февраля 2023", "Москва", "Общежитие 6"));
        userPoints.add(new UserPoint("Исакеевский собор", 59.928633, 30.293880, "Замечательное место отдыха", R.drawable.peterburg1));
        userPoints.add(new UserPoint("Красивый вид",59.950342, 30.369633, "Основная водная магистраль города: река Нева, которая впадает в Невскую губу Финского залива, относящегося к Балтийскому морю.", R.drawable.peterburg2));

    }

    public static MainService getInstance() {
        if(instance == null) instance = new MainService();
        return instance;
    }

    public List<Hostel> getAll() {
        return hostels;
    }

    public Hostel findHostel(int id) {
        for(Hostel h: hostels) if(h.id == id) return h;
        return null;
    }

    public List<Price> getPricesOfHostel(int hostelId) {
        List<Price> p = new ArrayList<>();
        p.add(new Price("3-х местный номер", "Предоставляется спальное место в трехместном номере.", 500));
        p.add(new Price("5-х местный номер", "Полноценный номер на 5 человек", 900));
        return p;
    }

    public List<Service> getServicesOfHostel(int hostelId){
        List<Service> s = new ArrayList<>();
        s.add(new Service("Завтрак", "Завтрак предоставляется по предварительной договоренности с организационным комитетом ИМЦ", 200));
        s.add(new Service("Завтрак и обед", "Завтрак и обед предоставляется по предварительной договоренности с организационным комитетом ИМЦ", 200));
        return s;
    }

    public List<NewsPost> getNewsPosts() {
        List<NewsPost> posts = new ArrayList<>();


        posts.add(new NewsPost("Ребята, это ОГНИЩЕ\uD83D\uDD25\uD83D\uDE03 Если вы давно мечтали сгонять в Калининград — начинайте искать билеты\uD83D\uDE09", new ArrayList<Integer>(Arrays.asList(new Integer[]{R.drawable.hostel_1, R.drawable.hostel_2, R.drawable.hostel_3, R.drawable.hostel_4})), 13));
        posts.add(new NewsPost("Друзья, «ИТОГИ ПУТЕШЕСТВИЙ ЗА 2022» подведены!\uD83D\uDD25 Поздравляем!\n" +
                "\n" +
                "А сегодня БОНУСНЫЙ вопрос:\n" +
                "\n" +
                "⃣⃣Какие у вас планы на следующий год? Сколько путешествий вы планируете и куда? Напишите в комментариях \uD83D\uDC47\uD83C\uDFFB\n" +
                "\n" +
                "Возможно, вы найдете попутчиков, объединитесь и поедете в турне по городам все вместе. Почему нет?\uD83D\uDE1C\n" +
                "\n" +
                "Так что расскажите нам прямо сейчас, куда вы хотите отправиться в следующем году✈", new ArrayList<Integer>(Arrays.asList(new Integer[]{R.drawable.hostel_1, R.drawable.hostel_2, R.drawable.hostel_3})), 67));
        posts.add(new NewsPost("До Нового года всего 4 дня, а это значит, что у нас осталось всего 4 вопроса в игре «ИТОГИ ПУТЕШЕСТВИЙ ЗА 2022»\uD83D\uDE09\n" +
                "\n" +
                "Вам вообще нравится? Ставьте ❤, если ДА.", new ArrayList<Integer>(Arrays.asList(new Integer[]{ R.drawable.hostel_3, R.drawable.hostel_4})), 89));
return posts;
    }

    public List<NewsPost> getUserNewsPosts(int id) {
        List<NewsPost> posts = new ArrayList<>();


        posts.add(new NewsPost("Ребята, это ОГНИЩЕ\uD83D\uDD25\uD83D\uDE03 Если вы давно мечтали сгонять в Калининград — начинайте искать билеты\uD83D\uDE09", new ArrayList<Integer>(Arrays.asList(new Integer[]{R.drawable.hostel_1, R.drawable.hostel_2, R.drawable.hostel_3, R.drawable.hostel_4})), 13));
        posts.add(new NewsPost("Друзья, «ИТОГИ ПУТЕШЕСТВИЙ ЗА 2022» подведены!\uD83D\uDD25 Поздравляем!\n" +
                "\n" +
                "А сегодня БОНУСНЫЙ вопрос:\n" +
                "\n" +
                "⃣⃣Какие у вас планы на следующий год? Сколько путешествий вы планируете и куда? Напишите в комментариях \uD83D\uDC47\uD83C\uDFFB\n" +
                "\n" +
                "Возможно, вы найдете попутчиков, объединитесь и поедете в турне по городам все вместе. Почему нет?\uD83D\uDE1C\n" +
                "\n" +
                "Так что расскажите нам прямо сейчас, куда вы хотите отправиться в следующем году✈", new ArrayList<Integer>(Arrays.asList(new Integer[]{R.drawable.hostel_1, R.drawable.hostel_2, R.drawable.hostel_3})), 67));
        posts.add(new NewsPost("До Нового года всего 4 дня, а это значит, что у нас осталось всего 4 вопроса в игре «ИТОГИ ПУТЕШЕСТВИЙ ЗА 2022»\uD83D\uDE09\n" +
                "\n" +
                "Вам вообще нравится? Ставьте ❤, если ДА.", new ArrayList<Integer>(Arrays.asList(new Integer[]{ R.drawable.hostel_3, R.drawable.hostel_4})), 89));
        return posts;
    }

    public void addBook(Book b){
        books.add(b);
    }

    public List<Book> getBooks(){
        return books;
    }

    public List<UserPoint> getUserPoints() {
        return userPoints;
    }

}
