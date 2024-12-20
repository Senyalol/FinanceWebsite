import React, { useState } from 'react';
import './aut.css'; 
import { Link, useNavigate } from 'react-router-dom';

function RegPage() {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!formData.username || !formData.password || !formData.email) {
            alert('Вы заполнили не все поля.');
            return;
        }

        // Убираем user_id из отправляемых данных
        const userData = {
            username: formData.username,
            email: formData.email,
            password: formData.password,
        };

        console.log('Данные для отправки:', JSON.stringify(userData, null, 2)); // Логируем данные

        try {
            const response = await fetch("http://localhost:8080/api/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
                console.log('Регистрация прошла успешно:', userData);
                navigate('/');
            } else {
                const errorData = await response.json(); // Получаем данные об ошибке
                console.error('Ошибка регистрации:', errorData);
                alert(`Ошибка: ${errorData.error || 'Неизвестная ошибка'}`);
            }
        } catch (error) {
            console.error('Ошибка при регистрации:', error);
            alert('Ошибка сети. Пожалуйста, проверьте соединение.');
        }
    };

    return (
        <div className="auth-body">
            <div className="auth-container">
                <h1>Регистрация</h1>
                <form onSubmit={handleSubmit}>
                    <div className="auth-form-group">
                        <label>Имя пользователя</label>
                        <input 
                            type="text" 
                            name="username" 
                            value={formData.username} 
                            onChange={handleChange} 
                        />
                    </div>
                    <div className="auth-form-group">
                        <label>Пароль</label>
                        <input 
                            type="password" 
                            name="password" 
                            value={formData.password} 
                            onChange={handleChange} 
                        />
                    </div>
                    <div className="auth-form-group">
                        <label>Эл. Почта</label>
                        <input 
                            type="email" 
                            name="email" 
                            value={formData.email} 
                            onChange={handleChange} 
                        />
                    </div>
                    <button type="submit" className="auth-button">Зарегистрироваться</button>
                </form>
                <div className="auth-footer">
                    <Link to="/">К авторизации</Link>
                </div>
            </div>
        </div>
    );
}

export default RegPage;