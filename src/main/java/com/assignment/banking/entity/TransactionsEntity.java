package com.assignment.banking.entity;

import com.assignment.banking.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "txn_seq", initialValue = 5, allocationSize = 9)
    private Integer id;

    @Column(nullable = false, length = 10, name = "txn_type")
    @Enumerated(EnumType.STRING)
    private TransactionType txnType;

    @Column(name = "txnId", nullable = true, length = 50)
    private String txnId;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "currencyCode", nullable = true)
    private String currencyCode;

    @Column(name = "remarks", nullable = false)
    private String remarks;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private AccountEntity accountEntity;

    @Column(name = "txnTime", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date txnTime;




}
