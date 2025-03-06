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

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MutationResolverTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private SavingsGoalRepository savingsGoalRepository;

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private DataLoader<Long, User> userLoader;

    @InjectMocks
    private MutationResolver mutationResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInvestment() {
        User user = new User();
        user.setId(1L);
        when(userLoader.load(1L)).thenReturn(CompletableFuture.completedFuture(user));
        when(investmentRepository.save(any(Investment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CompletableFuture<Investment> result = mutationResolver.createInvestment(1L, "type", "name", 100.0f, "2023-01-01", 150.0f, userLoader);
        assertNotNull(result);
        Investment investment = result.join();
        assertEquals("type", investment.getType());
        assertEquals("name", investment.getName());
        assertEquals(100.0f, investment.getAmount());
        assertEquals(LocalDate.parse("2023-01-01"), investment.getPurchaseDate());
        assertEquals(150.0f, investment.getCurrentValue());
        assertEquals(user, investment.getUser());
    }

    @Test
    void testCreateInvestmentUserNotFound() {
        when(userLoader.load(1L)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<Investment> future = mutationResolver.createInvestment(
                1L, "type", "name", 100.0f, "2023-01-01", 150.0f, userLoader
        );

        CompletionException completionException = assertThrows(CompletionException.class, future::join);
        assertTrue(completionException.getCause() instanceof UserNotFoundException);
    }

    @Test
    void testCreateExpense() {
        User user = new User();
        user.setId(1L);
        when(userLoader.load(1L)).thenReturn(CompletableFuture.completedFuture(user));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CompletableFuture<Expense> result = mutationResolver.createExpense(1L, "category", 100.0, "2023-01-01", userLoader);
        assertNotNull(result);
        Expense expense = result.join();
        assertEquals("category", expense.getCategory());
        assertEquals(100.0, expense.getAmount());
        assertEquals(LocalDate.parse("2023-01-01"), expense.getDate());
        assertEquals(user, expense.getUser());
    }

    @Test
    void testCreateExpenseUserNotFound() {
        when(userLoader.load(1L)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<Expense> result = mutationResolver.createExpense(
                1L, "category", 100.0, "2023-01-01", userLoader
        );

        CompletionException completionException =
                assertThrows(CompletionException.class, result::join);

        assertTrue(completionException.getCause() instanceof UserNotFoundException);
    }
}