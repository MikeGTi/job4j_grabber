package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.DateTimeParser;
import ru.job4j.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";

    private final DateTimeParser dateTimeParser;

    private int id = 1;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private Document retrieveDocument(String link) throws IOException {
        Connection connection = Jsoup.connect(link);
        return connection.get();
    }

    private String retrieveDescription(String link) {
        String rsl;
        try {
            rsl = retrieveDocument(link).select(".vacancy-description__text").first().text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rsl;
    }

    @Override
    public List<Post> list(String fullLink) {
        Document document;
        try {
            document = retrieveDocument(fullLink);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Post> rsl = new ArrayList<>();
        Elements rows = document.select(".vacancy-card__inner");
        for (Element row : rows) {
            Post post = retrieveVacancy(row, id);
            post.setText(retrieveDescription(post.getLink()));
            rsl.add(post);
            id++;
        }
        return rsl;
    }

    private Post retrieveVacancy(Element row, int id) {
        Element titleElement = row.select(".vacancy-card__title").first();
        String vacancyName = titleElement.text();
        Element linkElement = titleElement.child(0);
        String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        Element dateElement = row.select(".vacancy-card__date").first().child(0);
        return new Post(id, vacancyName, link, "", dateTimeParser.parse(dateElement.attr("datetime")));
    }

    public static void main(String[] args) {
        HabrCareerParse parser = new HabrCareerParse(new HabrCareerDateTimeParser());
        int pageNumber = 1;
        List<List<Post>> listOfListVacancies = new ArrayList<>();
        while (pageNumber < 6) {
            String fullLink = "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, pageNumber, SUFFIX);
            listOfListVacancies.add(parser.list(fullLink));
            pageNumber++;
        }
        List<Post> vacancies =
                listOfListVacancies.stream()
                        .flatMap(List::stream)
                        .toList();
        vacancies.forEach(System.out::println);
    }
}