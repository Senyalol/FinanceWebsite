// authorization.jsx
import React, { useState } from 'react';
import './aut.css'; // Подключаем CSS
import { Link, useNavigate } from 'react-router-dom'; // Импортируем useNavigate

function AutPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate(); // Получаем функцию для перехода

    const handleSubmit = (e) => {
        e.preventDefault();
        // Логика отправки данных для авторизации
        console.log('Email:', email);
        console.log('Password:', password);

        // После успешной авторизации (замените это условие на вашу логику):
        if (true) { 
            navigate('/start'); // Переходим на страницу /start
        }
    };

    return (
        <div className="auth-body"> {/* Добавили класс к body */}
            <div className="auth-container">
                <h1 className="auth-container h1">Авторизация</h1> {/* Добавили класс к заголовку */}
                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="auth-form-group"> {/* Переименовали класс */}
                        <label htmlFor="email" className="auth-form-group label"> {/* Переименовали класс */}Электронная почта</label>
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
                        <label htmlFor="password" className="auth-form-group label"> Пароль</label>
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
                        <p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AutPage;
