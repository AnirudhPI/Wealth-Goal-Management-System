package com.systems.finance.resolver;

import com.systems.finance.exception.UserNotFoundException;
import com.systems.finance.model.*;
import com.systems.finance.repository.*;
import org.dataloader.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryResolverTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private SavingsGoalRepository savingsGoalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private DataLoader<Long, User> userLoader;

    @InjectMocks
    private QueryResolver queryResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  // <-- Mock userRepository

        User result = queryResolver.getUser(1L);
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> queryResolver.getUser(1L));
    }

    @Test
    void getIncomes() {
        Income income = new Income();
        income.setId(1L);
        when(incomeRepository.findByUserId(1L)).thenReturn(Arrays.asList(income));

        List<Income> result = queryResolver.getIncomes(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(income, result.get(0));
    }

    @Test
    void getExpenses() {
        Expense expense = new Expense();
        expense.setId(1L);
        when(expenseRepository.findByUserId(1L)).thenReturn(Arrays.asList(expense));

        List<Expense> result = queryResolver.getExpenses(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expense, result.get(0));
    }

    @Test
    void getSavingsGoals() {
        SavingsGoal goal = new SavingsGoal();
        goal.setId(1L);

        when(savingsGoalRepository.findByUserId(1L))
                .thenReturn(Collections.singletonList(goal));

        List<SavingsGoal> result = queryResolver.getSavingsGoals(1L);

        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "List should contain exactly one SavingsGoal");
        assertEquals(goal, result.get(0), "The returned SavingsGoal should match the mock");
        verify(savingsGoalRepository).findByUserId(1L);
    }

    @Test
    void getInvestments() {
        Investment investment = new Investment();
        investment.setId(2L);

        when(investmentRepository.findByUserId(2L))
                .thenReturn(Collections.singletonList(investment));

        List<Investment> result = queryResolver.getInvestments(2L);

        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "List should contain exactly one Investment");
        assertEquals(investment, result.get(0), "The returned Investment should match the mock");
        verify(investmentRepository).findByUserId(2L);
    }

    @Test
    void getExpensesPaginated() {
        Expense expense = new Expense();
        expense.setId(1L);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Expense> mockPage = new PageImpl<>(
                Collections.singletonList(expense),
                pageable,
                1
        );

        when(expenseRepository.findByUserId(1L, pageable)).thenReturn(mockPage);

        Page<Expense> result = queryResolver.getExpensesPaginated(1L, 0, 10, "id");

        assertNotNull(result, "Resulting Page should not be null");
        assertEquals(1, result.getTotalElements(), "Total elements should be 1");
        assertEquals(expense, result.getContent().get(0), "First item should match the mocked Expense");
    }

    @Test
    void incomeUser() {
        Income income = new Income();
        User user = new User();
        user.setId(1L);
        income.setUser(user);

        when(userLoader.load(1L)).thenReturn(CompletableFuture.completedFuture(user));

        CompletableFuture<User> resultFuture = queryResolver.incomeUser(income, userLoader);

        assertNotNull(resultFuture, "CompletableFuture should not be null");
        User result = resultFuture.join();
        assertEquals(user, result, "Returned user should match the one in the mock");
        verify(userLoader).load(1L);
    }

    @Test
    void expenseUser() {
        Expense expense = new Expense();
        User user = new User();
        user.setId(2L);
        expense.setUser(user);

        when(userLoader.load(2L)).thenReturn(CompletableFuture.completedFuture(user));

        CompletableFuture<User> resultFuture = queryResolver.expenseUser(expense, userLoader);

        assertNotNull(resultFuture);
        User result = resultFuture.join();
        assertEquals(user, result);
        verify(userLoader).load(2L);
    }

    @Test
    void investmentUser() {
        Investment investment = new Investment();
        User user = new User();
        user.setId(3L);
        investment.setUser(user);

        when(userLoader.load(3L)).thenReturn(CompletableFuture.completedFuture(user));

        CompletableFuture<User> resultFuture = queryResolver.investmentUser(investment, userLoader);

        assertNotNull(resultFuture);
        User result = resultFuture.join();
        assertEquals(user, result);
        verify(userLoader).load(3L);
    }

    @Test
    void savingsGoalUser() {
        SavingsGoal savingsGoal = new SavingsGoal();
        User user = new User();
        user.setId(4L);
        savingsGoal.setUser(user);

        when(userLoader.load(4L)).thenReturn(CompletableFuture.completedFuture(user));

        CompletableFuture<User> resultFuture = queryResolver.savingsGoalUser(savingsGoal, userLoader);

        assertNotNull(resultFuture);
        User result = resultFuture.join();
        assertEquals(user, result);
        verify(userLoader).load(4L);
    }
}