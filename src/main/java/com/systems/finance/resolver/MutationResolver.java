package com.systems.finance.resolver;
import com.systems.finance.exception.UserNotFoundException;
import com.systems.finance.model.*;
import com.systems.finance.repository.*;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Controller
public class MutationResolver {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @MutationMapping
    public CompletableFuture<Investment> createInvestment(@Argument Long userId, @Argument String type, @Argument String name,
                                                          @Argument Float amount, @Argument String purchaseDate, @Argument Float currentValue,
                                                          DataLoader<Long, User> userLoader) {
        return userLoader.load(userId).thenApply(user -> {
            if(user == null) throw new UserNotFoundException(userId);
            Investment investment = new Investment();
            investment.setType(type);
            investment.setName(name);
            investment.setAmount(amount);
            investment.setPurchaseDate(LocalDate.parse(purchaseDate));
            investment.setCurrentValue(currentValue);
            investment.setUser(user);
            return investmentRepository.save(investment);
        });
    }

    @MutationMapping
    public Investment updateInvestment(@Argument Long id, @Argument String type, @Argument String name,
                                       @Argument Float amount, @Argument String purchaseDate, @Argument Float currentValue) {
        Investment investment = investmentRepository.findById(id).orElse(null);
        if (investment != null) {
            investment.setType(type);
            investment.setName(name);
            investment.setAmount(amount);
            investment.setPurchaseDate(LocalDate.parse(purchaseDate));
            investment.setCurrentValue(currentValue);
            investmentRepository.save(investment);
        }
        return investment;
    }

    @MutationMapping
    public boolean deleteInvestment(@Argument Long id) {
        if (investmentRepository.existsById(id)) {
            investmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public CompletableFuture<SavingsGoal> createSavingsGoal(@Argument Long userId, @Argument String description, @Argument Float targetAmount,
                                                            @Argument Float currentAmount, @Argument String targetDate,
                                                            DataLoader<Long, User> userLoader) {
        return userLoader.load(userId).thenApply(user -> {
            if(user == null) throw new UserNotFoundException(userId);
            SavingsGoal goal = new SavingsGoal();
            goal.setDescription(description);
            goal.setTargetAmount(targetAmount);
            goal.setCurrentAmount(currentAmount);
            goal.setTargetDate(LocalDate.parse(targetDate));
            goal.setUser(userRepository.findById(userId).orElse(null));
            return savingsGoalRepository.save(goal);
        });
    }

    @MutationMapping
    public SavingsGoal updateSavingsGoal(@Argument Long id, @Argument String description, @Argument Float targetAmount,
                                         @Argument Float currentAmount, @Argument String targetDate) {
        SavingsGoal goal = savingsGoalRepository.findById(id).orElse(null);
        if (goal != null) {
            goal.setDescription(description);
            goal.setTargetAmount(targetAmount);
            goal.setCurrentAmount(currentAmount);
            goal.setTargetDate(LocalDate.parse(targetDate));
            savingsGoalRepository.save(goal);
        }
        return goal;
    }

    @MutationMapping
    public boolean deleteSavingsGoal(@Argument Long id) {
        if (savingsGoalRepository.existsById(id)) {
            savingsGoalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public User createUser(@Argument String username, @Argument String password, @Argument String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    @MutationMapping
    public CompletableFuture<Income> createIncome(@Argument Long userId, @Argument String source, @Argument Double amount, @Argument String date,
                                                  DataLoader<Long, User> userLoader) {
        return userLoader.load(userId).thenApply(user -> {
            if(user == null) throw new UserNotFoundException(userId);
            Income income = new Income();
            income.setSource(source);
            income.setAmount(amount);
            income.setDate(LocalDate.parse(date));
            income.setUser(user);
            return incomeRepository.save(income);
        });
    }

    @MutationMapping
    public CompletableFuture<Expense> createExpense(@Argument Long userId, @Argument String category, @Argument Double amount, @Argument String date,
                                                    DataLoader<Long, User> userLoader) {
        return userLoader.load(userId).thenApply(user -> {
            if(user == null) throw new UserNotFoundException(userId);
            Expense expense = new Expense();
            expense.setCategory(category);
            expense.setAmount(amount);
            expense.setDate(LocalDate.parse(date));
            expense.setUser(user);
            return expenseRepository.save(expense);
        });
    }
}