import React from 'react';
import { useTranslation } from 'react-i18next';
import { ToggleButton, ToggleButtonGroup } from '@mui/material';

const LanguageSwitcher = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (event, language) => {
        if (language !== null) {
            i18n.changeLanguage(language);
            localStorage.setItem('i18nextLng', language);
        }
    }

    return (
        <ToggleButtonGroup
            value={i18n.language}
            exclusive
            onChange={changeLanguage}
        >
            <ToggleButton value="en">English</ToggleButton>
            <ToggleButton value="uk">Українська</ToggleButton>
        </ToggleButtonGroup>
    );
};

export default LanguageSwitcher;
