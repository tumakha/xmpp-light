package ua.tumakha.yuriy.xmpp.light.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ua.tumakha.yuriy.xmpp.light.domain.Message;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Yuriy Tumakha.
 */
public class SearchForm {

    private static final Logger LOG = LoggerFactory.getLogger(SearchForm.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("MM/dd/yyyy HH:mm");

    private String usernames;

    private String keywords;

    private String fromDate;

    private String toDate;

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Specification<Message> toSpecification() {
        Set<String> keywordsSet = splitKeywords(keywords);
        Set<String> usernamesSet = splitKeywords(usernames);
        Long from = getMilliseconds(fromDate);
        Long to = getMilliseconds(toDate);

        return (root, query, builder) -> {
            Predicate conjunction = builder.conjunction();

            if (keywordsSet != null) {
                Predicate disjunction = builder.disjunction();
                for (String keyword : keywordsSet) {
                    disjunction.getExpressions().add(builder.like(root.get("body"), keyword));
                }
                conjunction.getExpressions().add(disjunction);
            }
            if (usernamesSet != null) {
                Predicate disjunction = builder.disjunction();
                for (String username : usernamesSet) {
                    disjunction.getExpressions().add(builder.like(root.get("fromJID"), username));
                    disjunction.getExpressions().add(builder.like(root.get("toJID"), username));
                }
                conjunction.getExpressions().add(disjunction);
            }
            if (from != null) {
                conjunction.getExpressions().add(builder.greaterThanOrEqualTo(root.get("time"), from));
            }
            if (to != null) {
                conjunction.getExpressions().add(builder.lessThanOrEqualTo(root.get("time"), to));
            }

            if (conjunction.getExpressions().size() == 0) {
                return null;
            } else {
                return conjunction;
            }
        };
    }

    private Set<String> splitKeywords(String keywords) {
        return isEmpty(keywords) ? null
                : stream(keywords.replaceAll("^[,\\s]+", "").split("[,\\s]+"))
                .map(keyword -> "%" + keyword + "%").collect(toSet());
    }

    private Long getMilliseconds(String strDate) {
        Long millis = null;
        if (!isEmpty(strDate)) {
            try {
                millis = LocalDateTime.parse(strDate, DATE_TIME_FORMATTER)
                        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            } catch (Exception e) {
                LOG.error("Parse LocalDateTime failed.", e);
            }
        }
        return millis;
    }

}
