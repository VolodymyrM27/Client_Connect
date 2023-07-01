// RegisterPage.jsx
import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Typography,
    TextField,
    Button,
    Grid,
    Avatar,
    FormControl,
    InputLabel,
    Select,
    MenuItem
} from '@mui/material';
import UserContext from "../../context/user-context";
import {registerUser, createBusiness, getAllCategories} from "../../services/apiService";
import {useNavigate} from 'react-router-dom'
import {useTranslation} from "react-i18next";
import LanguageSwitcher from "../../components/LanguageSwitcher/LanguageSwitcher";

const RegisterPage = () => {
    let navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [businessName, setBusinessName] = useState('');
    const [address, setAddress] = useState('');
    const [category, setCategory] = useState(null);
    const [categories, setCategories] = useState([]);
    const userCtx = useContext(UserContext);
    const { t, i18n } = useTranslation();

    useEffect(() => {
        const fetchCategories = async () => {
            const response = await getAllCategories();
            setCategories(response);
        }
        fetchCategories();
    }, []);

    const handleSubmit = async () => {
        try {
            // 1. Регистрация пользователя

            const registerData = {
                email: email,
                password: password,
                role: 'BUSINESS',
            };
            const userResponse = await registerUser(registerData);

            // 2. Создание бизнеса
            const businessData = {
                user_id: userResponse.id,
                business_name: businessName,
                address: address,
                category: category
            };
            await createBusiness(businessData);

            // 3. Сохранение в контексте
            await userCtx.setUserCredentials(userResponse);

            navigate('/dashboard');
        } catch (error) {
            console.log(error);
        }
    };

    const handleSignInClick = () => {
        navigate('/login');
    };

    return (
        <Grid container sx={{height: '100vh'}}>
            <Grid item xs={12} sm={6} sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                p: 5,
                backgroundColor: '#FFF',
                boxShadow: '0px 0px 20px rgba(0,0,0,0.1)'
            }}>
                <Box sx={{width: '70%'}}>
                    <LanguageSwitcher />
                    <Typography variant="h4">Sign Up Account</Typography>

                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        fullWidth
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        sx={{mt: 3}}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        sx={{mt: 3}}
                    />

                    <TextField
                        label="Business Name"
                        type="text"
                        variant="outlined"
                        fullWidth
                        value={businessName}
                        onChange={(e) => setBusinessName(e.target.value)}
                        sx={{mt: 3}}
                    />

                    <TextField
                        label="Address"
                        type="text"
                        variant="outlined"
                        fullWidth
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        sx={{mt: 3}}
                    />

                    <FormControl sx={{mt: 3}} variant="outlined" fullWidth>
                        <InputLabel>Category</InputLabel>
                        <Select
                            label="Category"
                            value={category ? category.id : ''}
                            onChange={(e) => {
                                const selectedCategory = categories.find((c) => c.id === e.target.value);
                                setCategory(selectedCategory);
                            }}
                        >
                            {categories && categories.map((c) => (
                                <MenuItem key={c.id} value={c.id}>
                                    {c.category_name}
                                </MenuItem>
                            ))}
                        </Select>

                    </FormControl>

                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{mt: 3, borderRadius: '12px'}}
                        onClick={handleSubmit}
                    >
                        Sign up
                    </Button>

                    <Typography variant="body1" sx={{mt: 3, fontSize: '18px', fontWeight: 'bold'}}>
                        Already have an account?
                        <span style={{color: 'blue'}} onClick={handleSignInClick}>Sign in!</span>
                    </Typography>
                </Box>
            </Grid>

            <Grid item xs={12} sm={6} sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: '#afdc77',
                color: 'black',
                p: 5
            }}>
                <Box sx={{width: '70%'}}>
                    <Typography variant="h4" sx={{fontWeight: 'bold', fontSize: '28px'}}>
                        Explore the best system for providing personalized service in the service industry
                    </Typography>

                    <Typography variant="body1" sx={{mt: 3}}>
                        Hundreds of businesses have already joined us and have greatly improved their customer service!
                        Join us too!
                    </Typography>

                    <Box sx={{display: 'flex', justifyContent: 'center', mt: 3, '& > *': {m: 0.5}}}>
                        <Avatar/> <Avatar/> <Avatar/> <Avatar/>
                    </Box>

                    <Box sx={{display: 'flex', alignItems: 'center', mt: 3}}>
                        <hr style={{flex: '1'}}/>
                        <Typography variant="h6" sx={{mx: 2}}>150+ happy customers!</Typography>
                        <hr style={{flex: '1'}}/>
                    </Box>
                </Box>
            </Grid>
        </Grid>
    );
}

export default RegisterPage;


