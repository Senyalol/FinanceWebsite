import React, { useState } from 'react';
import './transactions.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMoneyBillWave, faTrash } from '@fortawesome/free-solid-svg-icons';
import { library } from '@fortawesome/fontawesome-svg-core';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Link, useParams } from 'react-router-dom';

ChartJS.register(ArcElement, Tooltip, Legend);

library.add(faMoneyBillWave);
library.add(faTrash);

const TransactionsPage = () => {
    const [transactions, setTransactions] = useState([]);
    const [newTransaction, setNewTransaction] = useState({ date: '', description: '', amount: '' });
    const { id } = useParams(); // user_id

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewTransaction({ ...newTransaction, [name]: value });
    };

    const handleSubmit = async (e) => {
      e.preventDefault();
      if (!newTransaction.date || !newTransaction.description || !newTransaction.amount) {
          alert('Пожалуйста, заполните все поля.');
          return;
      }
  
      const amount = parseFloat(newTransaction.amount);
      const transactionData = {
          user_id: id, // user_id из URL
          category_id: 2, // Исправлено на category_id
          amount: amount, // Сумма как BigDecimal
          date: newTransaction.date + 'T00:00:00Z', // Формат даты
          description: newTransaction.description,
      };
  
      console.log('Отправляемые данные:', JSON.stringify(transactionData));
  
      try {
          const response = await fetch('http://localhost:8080/api/transactions', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify(transactionData),
          });
  
          if (!response.ok) {
              const errorData = await response.json();
              console.error('Ошибка при отправке транзакции:', errorData);
              alert(`Ошибка: ${errorData.error}`);
              return;
          }
  
          const result = await response.json();
          console.log('Транзакция успешно добавлена:', result);
  
          setTransactions([...transactions, { ...transactionData, id: Date.now() }]);
          setNewTransaction({ date: '', description: '', amount: '' });
      } catch (error) {
          console.error('Ошибка:', error);
      }
  };

    const handleDelete = (id) => {
        const updatedTransactions = transactions.filter(transaction => transaction.id !== id);
        setTransactions(updatedTransactions);
    };

    const positiveTransactions = transactions.filter(t => t.amount > 0).map(t => t.amount);
    const negativeTransactions = transactions.filter(t => t.amount < 0).map(t => t.amount);

    const chartData = {
        labels: ['Поступления', 'Отправления'],
        datasets: [{
            label: 'Транзакции',
            data: [positiveTransactions.reduce((a, b) => a + b, 0) || 0, Math.abs(negativeTransactions.reduce((a, b) => a + b, 0)) || 0],
            backgroundColor: ['rgba(75, 192, 192, 0.6)', 'rgba(255, 99, 132, 0.6)'],
            borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
            borderWidth: 1,
        }],
    };

    const options = {
        plugins: {
            legend: {
                labels: {
                    color: 'white',
                },
            },
        },
        responsive: true,
        maintainAspectRatio: false,
    };

    return (
        <div className="transactionPage-transactionsContainer">
            <h1 className="transactionPage-transactionsHeader">
                <FontAwesomeIcon icon={faMoneyBillWave} className="me-2" />
                Список транзакций
            </h1>

            <Link to={`/start/${id}`}>
                <button className="FromTransactionsToStartBack-button">Назад</button>
            </Link>

            <div className="transaction-container">
                <form onSubmit={handleSubmit} className="mb-4 transaction-form">
                    <div className="mb-3">
                        <label htmlFor="date">Дата:</label>
                        <input type="date" name="date" value={newTransaction.date} onChange={handleChange} className="form-control" required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description">Описание:</label>
                        <input type="text" name="description" value={newTransaction.description} onChange={handleChange} className="form-control" required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="amount">Сумма:</label>
                        <input type="number" name="amount" value={newTransaction.amount} onChange={handleChange} className="form-control" required />
                    </div>
                    <button type="submit" className="btn btn-primary">Добавить</button>
                </form>
                <div className="table-chart-container">
                    <div className="transactions-table-container">
                        <table className="transactionPage-transactionsTable table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Дата</th>
                                    <th scope="col">Описание</th>
                                    <th scope="col">Сумма</th>
                                    <th scope="col">Действие</th>
                                </tr>
                            </thead>
                            <tbody>
                                {transactions.map((transaction) => (
                                    <tr key={transaction.id}>
                                        <td>{transaction.date}</td>
                                        <td>{transaction.description}</td>
                                        <td className={transaction.amount < 0 ? 'text-danger' : 'text-success'}>
                                            {transaction.amount}
                                        </td>
                                        <td>
                                            <FontAwesomeIcon icon={faTrash} onClick={() => handleDelete(transaction.id)} className="delete-icon" />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="chart-container">
                        <div style={{ width: '300px', height: '300px', maxWidth: '100%' }}>
                            <h2 className="text-white">Диаграмма транзакций</h2>
                            <Doughnut data={chartData} options={options} />
                        </div>
                    </div>
                </div>
            </div>

            <footer className="transactionPage-transactionsFooter">
                <p>&copy; 2024 Financial Control Application</p>
            </footer>
        </div>
    );
};

export default TransactionsPage;