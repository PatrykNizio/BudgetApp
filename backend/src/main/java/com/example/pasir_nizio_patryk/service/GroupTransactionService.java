package com.example.pasir_nizio_patryk.service;

import com.example.pasir_nizio_patryk.dto.GroupTransactionDTO;
import com.example.pasir_nizio_patryk.model.Debt;
import com.example.pasir_nizio_patryk.model.Group;
import com.example.pasir_nizio_patryk.model.Membership;
import com.example.pasir_nizio_patryk.model.User;
import com.example.pasir_nizio_patryk.repository.DebtRepository;
import com.example.pasir_nizio_patryk.repository.GroupRepository;
import com.example.pasir_nizio_patryk.repository.MembershipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTransactionService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final DebtRepository debtRepository;

    public GroupTransactionService(GroupRepository groupRepository, MembershipRepository membershipRepository, DebtRepository debtRepository) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.debtRepository = debtRepository;
    }

    public void addGroupTransaction(GroupTransactionDTO dto, User currentUser) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy"));

        List<Membership> members = membershipRepository.findByGroupId(group.getId());
        List<Long> selectedUserIds = dto.getSelectedUserIds();

        if(selectedUserIds == null || selectedUserIds.isEmpty()) {
            throw new IllegalArgumentException("Nie wybrano żadnych użytkowników");
        }
        if (members.isEmpty()) {
            throw new IllegalArgumentException("Brak członków w grupie!");
        }
        double amountPerUser = dto.getAmount() / selectedUserIds.size();

        for (Membership member : members) {
            User debtor = member.getUser();
            if (!debtor.getId().equals(currentUser.getId()) && selectedUserIds.contains(debtor.getId())) {
                Debt debt = new Debt();
                debt.setDebtor(debtor);
                debt.setCreditor(currentUser);
                debt.setGroup(group);
                debt.setAmount(amountPerUser);
                debt.setTitle(dto.getTitle());
                debtRepository.save(debt);
            }
        }
    }
}
