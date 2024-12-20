import React, { useState, useEffect } from 'react';
import { Doughnut } from 'react-chartjs-2';
import './categories.css';
import { Link, useParams } from 'react-router-dom';

function CategoryPage() {
    const [savings, setSavings] = useState([]);
    const [savingName, setSavingName] = useState('');
    const [targetAmount, setTargetAmount] = useState('');
    const [addAmount, setAddAmount] = useState('');
    const [selectedSavingId, setSelectedSavingId] = useState(null);
    const [category, setCategory] = useState('На машину');
    const { id } = useParams();
    const [categories, setCategories] = useState([]); // Состояние для категорий

    const fetchCategories = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/categories?user_id=${id}`);
            if (!response.ok) {
                throw new Error('Ошибка при загрузке категорий');
            }
            const data = await response.json();
            setCategories(data);
        } catch (error) {
            console.error('Ошибка при получении категорий:', error);
        }
    };

    useEffect(() => {
        fetchCategories(); // Загружаем категории при монтировании
    }, [id]);

    const handleCreatingSaving = () => {
        if (savingName && targetAmount > 0 && !savings.find(s => s.name === savingName)) {
            const newSaving = {
                id: savings.length + 1,
                name: savingName,
                addedAmount: 0,
                amount: Number(targetAmount),
                category: category,
            };
            setSavings(prevSavings => [...prevSavings, newSaving]);
            setSavingName('');
            setTargetAmount('');
            setCategory('На машину');
        } else {
            console.log("Ошибка при создании накопления: неверные данные");
        }
    };

    const handleAddingAmount = () => {
        if (selectedSavingId && addAmount > 0) {
            const updatedSavings = savings.map(saving =>
                saving.id === selectedSavingId
                    ? { ...saving, addedAmount: saving.addedAmount + Number(addAmount) }
                    : saving
            );

            setSavings(updatedSavings);
            setAddAmount('');
        } else {
            console.log("Ошибка: нет текущего накопления или неверная сумма для добавления");
        }
    };

    const selectedSaving = savings.find(s => s.id === selectedSavingId);

    const handleSubmit = async () => {
        if (savingName && targetAmount > 0) {
            const newCategory = {
                categories_id: savings.length + 1,
                user_id: id,
                name: savingName,
                type: targetAmount.toString(),
            };

            try {
                const response = await fetch('http://localhost:8080/api/categories', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(newCategory),
                });

                if (!response.ok) {
                    throw new Error('Ошибка при отправке данных');
                }

                const result = await response.json();
                console.log('Категория успешно добавлена:', result);
                handleCreatingSaving();
            } catch (error) {
                console.error('Ошибка:', error);
            }
        } else {
            console.log("Ошибка: неверные данные для отправки");
        }
    };

    return (
        <div className="categoriesPage-categoriesContainer">
            <header className="categoriesHeader">Категории накоплений</header>

            <Link to={`/start/${id}`}>
                <button className="FromCategoriessToStartBack-button">Назад</button>
            </Link>

            <main className="categoriesContent">
                <div className="inputSection">
                    <div className="inputContainer">
                        <label htmlFor="savingName">Введите название накопления:</label>
                        <input
                            id="savingName"
                            type="text"
                            value={savingName}
                            onChange={(e) => setSavingName(e.target.value)}
                            placeholder="Название накопления"
                        />
                    </div>

                    <div className="inputContainer">
                        <label htmlFor="targetAmount">Введите сумму накопления:</label>
                        <input
                            id="targetAmount"
                            type="number"
                            value={targetAmount}
                            onChange={(e) => setTargetAmount(e.target.value)}
                            placeholder="Сумма для накопления"
                        />
                    </div>

                    <div className="inputContainer">
                        <label htmlFor="category">Выберите категорию:</label>
                        <select id="category" value={category} onChange={(e) => setCategory(e.target.value)}>
                            <option value="На машину">На машину</option>
                            <option value="На бытовые вещи">На бытовые вещи</option>
                            <option value="На отпуск">На отпуск</option>
                        </select>
                    </div>
                    <button className="actionButton" onClick={handleSubmit}>Создать накопление</button>
                </div>

                <div className="savingsContainer">
                    <h2>Ваши накопления:</h2>
                    <div className="inputContainer">
                        <label htmlFor="savingsSelect">Выберите накопление для редактирования суммы:</label>
                        <select 
                            id="savingsSelect" 
                            value={selectedSavingId || ''} 
                            onChange={(e) => setSelectedSavingId(Number(e.target.value))}
                        >
                            <option value="">--Выберите накопление--</option>
                            {categories.map(category => (
                                <option key={category.categories_id} value={category.categories_id}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    {selectedSaving && (
                        <div>
                            <p>{selectedSaving.name} (Категория: {selectedSaving.category})</p>
                            <div className="inputContainer">
                                <input
                                    type="number"
                                    value={addAmount}
                                    onChange={(e) => setAddAmount(e.target.value)}
                                    placeholder="Введите сумму для добавления"
                                />
                                <button onClick={handleAddingAmount}>Добавить</button>
                            </div>
                        </div>
                    )}
                </div>

                <div className="chartSection">
                    {selectedSaving && (
                        <div className="chartContainer">
                            <h3 style={{ color: 'white', fontSize: '24px' }}>
                                {selectedSaving.name}
                            </h3>
                            <Doughnut
                                data={{
                                    labels: ['Накопленная сумма', 'Оставшаяся сумма'],
                                    datasets: [{
                                        data: [
                                            selectedSaving.addedAmount, 
                                            selectedSaving.amount - selectedSaving.addedAmount
                                        ],
                                        backgroundColor: ['rgba(75, 192, 192, 0.6)', 'rgba(201, 203, 207, 0.6)'],
                                    }],
                                }}
                                options={{
                                    plugins: {
                                        legend: {
                                            labels: {
                                                color: 'white',
                                                font: {
                                                    size: 16,
                                                },
                                            },
                                        },
                                    },
                                }}
                            />
                        </div>
                    )}
                </div>
            </main>

            <footer className="categoriesPage-categoriesFooter">
                &copy; 2024 Financial Control Application
            </footer>
        </div>
    );
}

export default CategoryPage;