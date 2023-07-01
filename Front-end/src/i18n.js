import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from 'i18next-browser-languagedetector';

i18n
    .use(initReactI18next)
    .use(LanguageDetector)
    .init({
        resources: {
            en: {
                translations: {
                    "Sign in Account": "Sign in Account",
                    "Sign in": "Sign in",
                    "Still don't have an account?": "Still don't have an account?",
                    "Sign up!": "Sign up!",
                    "Explore the best system": "Explore the best system for providing personalized service in the service industry",
                    "Join us too!": "Join us too!",
                    "happy customers!": "150+ happy customers!",
                    "Password": "Password",
                    "Email": "Email",
                    "Visits": "Visits",
                    "Average rating by reviews": "Average rating by reviews",
                    "ratingGlobal": "This rating is an indicator of how the system evaluates you. If you have just registered with the system, and your business is not very large yet, then you will have a not very high score. The more you use the system and the more you promote your business, the higher your score will be. The rating is made up of such factors as business rating, number of reviews, number of visits, number of users who have returned to you more than once.",
                    "Global Rating": "Global Rating",
                    "Most popular visitor": "Most popular visitor",
                    "First Name": "First Name",
                    "Last Name": "Last Name",
                    "Action": "Action",

                }
            },
            uk: {
                translations: {
                    "Sign in Account": "Увійдіть в аккаунт",
                    "Sign in": "Увійти",
                    "Still don't have an account?": "Ще немаєте облікового запису?",
                    "Sign up!": "Зареєструйтесь!",
                    "Explore the best system": "Дослідіть найкращу систему для надання персоналізованого обслуговування в галузі обслуговування",
                    "Join us too!": "Приєднуйтесь до нас теж!",
                    "happy customers!": "150+ задоволених клієнтів!",
                    "Password": "пароль",
                    "Email": "пошта",
                    "Visits": "Візити",
                    "Average rating by reviews": "Середня оцінка за відгуками",
                    "ratingGlobal": "Цей рейтинг - показник того, як система оцінює вас. Якщо ви щойно зареєструвалися в системі, і ваш бізнес ще не дуже великий, то у вас буде не дуже високий бал. Чим більше ви користуєтеся системою і чим більше просуваєте свій бізнес, тим вищим буде ваш бал. Рейтинг складається з таких факторів, як рейтинг бізнесу, кількість відгуків, кількість відвідувань, кількість користувачів, які повернулися до вас більше одного разу\n",
                    "Global Rating": "Оцінка системою",
                    "Most popular visitor": "Найпопулярніший відвідувач",
                    "First Name": "Імʼя",
                    "Last Name": "призвище",
                    "Action": "дія",
                }
            },
        },
        fallbackLng: "en",
        debug: true,

        ns: ["translations"],
        defaultNS: "translations",

        keySeparator: false,

        interpolation: {
            escapeValue: false,
            formatSeparator: ","
        },

        react: {
            wait: true
        }
    });

export default i18n;
