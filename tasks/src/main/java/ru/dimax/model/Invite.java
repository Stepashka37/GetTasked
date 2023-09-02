package ru.dimax.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invites")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment", length = 500)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private User invited;

    @ManyToOne
    private Task task;

    @Column(name = "accepted")
    private Boolean accepted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return Objects.equals(id, invite.id) && Objects.equals(comment, invite.comment) && Objects.equals(inviter, invite.inviter) && Objects.equals(invited, invite.invited) && Objects.equals(task, invite.task) && Objects.equals(accepted, invite.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, inviter, invited, task, accepted);
    }
}
