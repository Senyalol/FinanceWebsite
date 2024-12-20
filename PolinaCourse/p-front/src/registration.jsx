import React, { useState } from 'react';
import './aut.css'; 
import { Link, useNavigate } from 'react-router-dom';

function RegPage() {
    const [formData, setFormData] = useState({
        name: '',
        password: '',
        email: '',
        age: '',
        avatar: ''
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!formData.name || !formData.password || !formData.email || !formData.age) {
            alert('Вы заполнили не все поля.');
            return;
        }
        console.log('Регистрация прошла успешно:', formData);
        navigate('/user', { state: formData }); // Pass formData to user page
    };

    return (
        <div className="auth-body">
            <div className="auth-container">
                <h1>Register</h1>
                <form onSubmit={handleSubmit}>
                    <div className="auth-form-group">
                        <label>Имя</label>
                        <input type="text" name="name" value={formData.name} onChange={handleChange} />
                    </div>
                    <div className="auth-form-group">
                        <label>Пароль</label>
                        <input type="password" name="password" value={formData.password} onChange={handleChange} />
                    </div>
                    <div className="auth-form-group">
                        <label>Эл. Почта</label>
                        <input type="email" name="email" value={formData.email} onChange={handleChange} />
                    </div>
                    <div className="auth-form-group">
                        <label>Возраст</label>
                        <input type="number" name="age" value={formData.age} onChange={handleChange} />
                    </div>
                    <div className="auth-form-group">
                        <label>Аватар (по желанию)</label>
                        <input type="text" name="avatar" value={formData.avatar} onChange={handleChange} />
                        <small>Выберите эмодзи</small>
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