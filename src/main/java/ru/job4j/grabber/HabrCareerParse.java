package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.DateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    public static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";
    private final DateTimeParser dateTimeParser;

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

    private Post retrieveVacancy(Element row) {
        Element titleElement = row.select(".vacancy-card__title").first();
        String vacancyName = titleElement.text();
        Element linkElement = titleElement.child(0);
        String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
        Element dateElement = row.select(".vacancy-card__date").first().child(0);
        return new Post(vacancyName, link, "", dateTimeParser.parse(dateElement.attr("datetime")));
    }

    @Override
    public List<Post> list(String emptyLink) {
        List<List<Post>> listOfListVacancies = new ArrayList<>();
        int pageNumber = 1;
        while (pageNumber < 6) {
            String fullLink = "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, pageNumber, SUFFIX);
            listOfListVacancies.add(getPostsFromPage(fullLink));
            pageNumber++;
        }
        return listOfListVacancies.stream()
                .flatMap(List::stream)
                .toList();
    }

    private List<Post> getPostsFromPage(String link) {
        Document document;
        try {
            document = retrieveDocument(link);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Post> rsl = new ArrayList<>();
        Elements rows = document.select(".vacancy-card__inner");
        for (Element row : rows) {
            Post post = retrieveVacancy(row);
            post.setText(retrieveDescription(post.getLink()));
            rsl.add(post);
        }
        return rsl;
    }
}