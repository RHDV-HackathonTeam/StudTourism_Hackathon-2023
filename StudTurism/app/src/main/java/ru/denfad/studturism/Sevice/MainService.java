package ru.denfad.studturism.Sevice;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.denfad.studturism.Model.Book;
import ru.denfad.studturism.Model.Hostel;
import ru.denfad.studturism.Model.NewsPost;
import ru.denfad.studturism.Model.UserPoint;
import ru.denfad.studturism.Model.Rate;
import ru.denfad.studturism.Model.Service;
import ru.denfad.studturism.R;

public class MainService {

    private List<Hostel> hostels = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<UserPoint> userPoints = new ArrayList<>();

    private static MainService instance;

    private MainService() {
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

    public List<Rate> getPricesOfHostel(int hostelId) {
        List<Rate> p = new ArrayList<>();

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

    public void addUserPoint(UserPoint userPoint) {
        userPoints.add(userPoint);
    }

    public List<String> getRegions(){
        List<String> array = Arrays.asList(new String[]{"Алтайский край", "Алтайский край", "Амурская область", "Архангельская область", "Астраханская область", "Белгородская область", "Белгородская область,город Белгород", "Владимирская область", "Волгоградская область", "Вологодская область", "Еврейская автономная область", "Забайкальский край", "Ивановская область", "Иркутская область", "Калининградская область", "Калужская область", "Карачаево-Черкесская Республика", "Кемеровская область", "Кировская область", "Костромская область", "Красноярский край", "Курганская область", "Курская область", "Москва", "Московская область", "Мурманская область", "Нижегородская область", "Новгородская область", "Новосибирская область", "Омская область", "Оренбургская область", "Орловская область", "Пензенская область", "Пермский край", "Приморский край", "Псковская область", "Республика Адыгея", "Республика Алтай", "Республика Башкортостан", "Республика Бурятия", "Республика Дагестан", "Республика Ингушетия", "Республика Калмыкия", "Республика Карелия", "Республика Коми", "Республика Крым", "Республика Марий Эл", "Республика Мордовия", "Республика Северная Осетия - Алания", "Республика Татарстан", "Республика Тыва, город Кызыл", "Республика Хакасия", "Ростовская область", "Рязанская область", "Самарская область", "Санкт-Петербург", "Саратовская область", "Свердловская область", "Севастополь", "Смоленская область", "Ставропольский край", "Тамбовская область", "Томская область", "Тульская область", "Тюменская область", "Удмуртская Республика", "Ульяновская область", "Хабаровский край", "Ханты-Мансийский автономный округ", "Челябинская область", "Чеченская республика", "Чувашская Республика"});
        return array;
    }

    public List<String> getFederals(){
        List<String> array = Arrays.asList(new String[]{"Дальневосточный",
                "Приволжский",
                "Северо-Западный",
                "Северо-Кавказский",
                "Сибирский"});
        return array;
    }

    public List<String> getTowns(){
        List<String> array = Arrays.asList(new String[]{"Апатиты", "Артыбаш", "Архангельск", "Астрахань", "Байкал оз., с. Максимиха, респ Бурятия", "Байкал, оз. (Иркутск)", "Балашиха", "Барнаул", "Белгород", "Белгородская область, Белгородский район, п. Майский", "Бийск", "Биробиджан", "Благовещенск", "Великий Новгород", "Владивосток", "Владивосток, остров Русский", "Владикавказ", "Владимир", "Волгоград", "Глазов", "Горно-Алтайск", "Грозный", "Екатеринбург", "Заокский", "Иваново", "Ижевск", "Иркутск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Карачаево-Черкесская республика, поселок Архыз", "Карачаевск", "Кемерово", "Кемеровская область, пос. Шерегеш", "Киров", "Ковров", "Кострома", "Красноярск", "Курган", "Курск", "Кызыл", "Магас", "Магнитогорск", "Майкоп", "Махачкала", "Москва", "Московская область, Пушкинский городской округ, дп. Черкизово", "Мурманск", "Нижний Новгород", "Новосибирск", "Новый Шарап, село", "Одинцово", "Омск", "Орел", "Оренбург", "Пенза", "Пермь", "Петрозаводск", "Псков", "Пущино", "Пятигорск", "Республика Бурятия, Кабанский район", "Республика Карелия, Пряжинский район, 25 км от г. Петрозаводска", "Республика Хакасия, г. Абакан", "Ростов-на-Дону", "Рязань", "Самара", "Санкт-Петербург", "Саранск", "Саратов", "Севастополь", "Симферополь", "Смоленск", "Ставрополь", "Сыктывкар", "Таганрог", "Тамбов", "Теберда", "Тольятти", "Томск", "Тула", "Тюмень", "Улан-Удэ", "Ульяновск", "Уссурийск", "Уфа", "Хабаровск", "Ханты-Мансийск", "Чайковский", "Чарышский район", "Чебоксары", "Челябинск", "Чемальский район, Республика Алтай", "Череповец", "Чита", "Элиста", "г. Санкт-Петербург, пос. Шушары", "оз. Щучье"});
        return array;
    }


}
