package nextstep.domain;

import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();


    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public boolean isOwners(User loginUser) {
        for (Answer answer : answers) {
            if (!answer.isOwner(loginUser)) {
                return false;
            }
        }
        return true;
    }

    public DeleteHistories addDeleteHistories(DeleteHistories deleteHistories) {
        for (Answer answer : answers) {
            deleteHistories.addDeleteHistory(answer.getDeleteHistoryByAnswer());
        }
        return deleteHistories;
    }
}
