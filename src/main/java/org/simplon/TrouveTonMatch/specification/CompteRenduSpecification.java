package org.simplon.TrouveTonMatch.specification;

import org.simplon.TrouveTonMatch.model.CompteRendu;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CompteRenduSpecification {

    public static Specification<CompteRendu> hasProjetTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("projet").get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<CompteRendu> hasPorteurLastname(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("projet").get("porteur").get("lastname")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<CompteRendu> hasParrainLastname(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("parrain").get("lastname")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<CompteRendu> hasProchainRdv(LocalDate date) {
        return (root, query, cb) ->
                date == null ? null : cb.equal(root.get("prochainRdv"), date);
    }
}
