package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.data.jpa.domain.Specification;
import ua.tumakha.yuriy.xmpp.light.domain.Message;

import javax.persistence.criteria.Predicate;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Yuriy Tumakha.
 */
public class SearchForm {

    private String usernames;

    private String keywords;

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

    public Specification<Message> toSpecification() {
        Set<String> keywordsSet = splitKeywords(keywords);
        Set<String> usernamesSet = splitKeywords(usernames);

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

}
