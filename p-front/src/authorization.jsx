import React, { useState } from 'react';
import './aut.css'; // Подключаем CSS
import { Link, useNavigate } from 'react-router-dom'; // Импортируем useNavigate

function AutPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // Получаем функцию для перехода

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Получаем список пользователей
            const response = await fetch("http://localhost:8080/api/users");
            const users = await response.json();

            // Проверяем, существует ли пользователь с введенными данными
            const user = users.find(u => u.email === email && u.password === password);

            if (user) {
                // Если пользователь найден, перенаправляем на страницу с его ID
                navigate(`/start/${user.user_id}`);
            } else {
                alert('Неправильный email или пароль.');
            }
        } catch (error) {
            console.error('Ошибка при авторизации:', error);
            alert('Ошибка сети. Пожалуйста, проверьте соединение.');
        }
    };

    return (
        <div className="auth-body"> {/* Добавили класс к body */}
            <div className="auth-container">
                <h1 className="auth-container h1">Авторизация</h1> {/* Добавили класс к заголовку */}
                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="auth-form-group"> {/* Переименовали класс */}
                        <label htmlFor="email" className="auth-form-group label">Электронная почта</label>
                        <input 
                            type="email" 
                            id="email" 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            required 
                            className="auth-form-group input" 
                        />
                    </div>
                    <div className="auth-form-group"> 
                        <label htmlFor="password" className="auth-form-group label">Пароль</label>
                        <input 
                            type="password" 
                            id="password" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            required 
                            className="auth-form-group input" 
                        />
                    </div>
                    <button type="submit" className="auth-button">Войти</button>
                    <div className="auth-footer">
                        <p>Нет аккаунта? <Link to="/register">Зарегистрироваться</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AutPage;