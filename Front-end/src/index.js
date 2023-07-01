import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { UserContextProvider } from "./context/user-context";
import { ConfirmProvider } from 'material-ui-confirm';
import { I18nextProvider } from 'react-i18next';
import i18n from './i18n';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <UserContextProvider>
        <ConfirmProvider>
            <I18nextProvider i18n={i18n}>
                <App />
            </I18nextProvider>
        </ConfirmProvider>
    </UserContextProvider>
);

reportWebVitals();
