// index.js
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import AutPage from './authorization.jsx';
import StartPage from './start.jsx';
import FinancePage from './finance.jsx'; 
import CategoryPage from './categories.jsx';
import UserPage from './user.jsx';
import RegPage from './registration.jsx';
import TransactionsPage from './transactions.jsx'; // Импорт компонента TransactionsPage
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<AutPage />} />
      <Route path="/start" element={<StartPage />} />
      <Route path="/finance" element={<FinancePage />} /> 
      <Route path="/transactions" element={<TransactionsPage />} /> 
      <Route path="/category" element ={<CategoryPage/>}/>
      <Route path="/user" element = {<UserPage/>}></Route>
      <Route path="/register" element = {<RegPage/>}></Route>
    </Routes>
  </BrowserRouter>
);

reportWebVitals();

