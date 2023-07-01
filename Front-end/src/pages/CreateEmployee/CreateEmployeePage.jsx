import React, {useState, useContext} from 'react';
import {useForm} from "react-hook-form";
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Sidebar from '../../components/Sidebar';
import {Container, TextField, Button, Typography, Grid, Box, Select, MenuItem, FormControl, InputLabel} from '@mui/material';
import UserContext from '../../context/user-context';
import {createEmployee} from "../../services/apiService";

function CreateEmployeePage() {
    const [loading, setLoading] = useState(false);
    const {businessId} = useContext(UserContext);
    const {register, handleSubmit} = useForm({
        defaultValues: {
            gender: "Male"
        }
    });


    const onSubmit = async (data) => {
        setLoading(true);
        try {
            const employeeData = {
                registerRequest: {
                    email: data.email,
                    password: data.password,
                    role: "EMPLOYEE",
                },
                userProfileDTO: {
                    first_name: data.firstName,
                    last_name: data.lastName,
                    date_of_birth: data.dateOfBirth,
                    gender: data.gender.toString(),
                    contact_number: data.contactNumber,
                    country: data.country,
                    state: data.state,
                    city: data.city,
                }
            };

            await createEmployee(employeeData,businessId);
            toast.success("Employee created successfully!");
            setLoading(false);
        } catch (error) {
            toast.error("Error creating employee.");
            setLoading(false);
        }
    };

    return (
        <Box sx={{display: 'flex'}}>
            <Sidebar/>
            <Container sx={{marginTop: 3, marginLeft: '240px'}}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <Typography variant="h4">Create Employee</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={6}>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('email')} label="Email" variant="outlined" required fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('password')} label="Password" type="password" variant="outlined"
                                           required fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('firstName')} label="First Name" variant="outlined" required
                                           fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('lastName')} label="Last Name" variant="outlined" required
                                           fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('dateOfBirth')} label="Date of Birth" type="date"
                                           InputLabelProps={{shrink: true}} variant="outlined" required fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <FormControl fullWidth>
                                    <InputLabel id="gender-label">Gender</InputLabel>
                                    <Select
                                        {...register('gender')}
                                        labelId="gender-label"
                                        label="Gender"
                                        variant="outlined"
                                        required
                                    >
                                        <MenuItem value={"Male"}>Male</MenuItem>
                                        <MenuItem value={"Female"}>Female</MenuItem>
                                    </Select>
                                </FormControl>
                            </Box>
                        </Grid>
                        <Grid item xs={6}>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('contactNumber')} label="Contact Number" variant="outlined" required
                                           fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('country')} label="Country" variant="outlined" required fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('state')} label="State" variant="outlined" required fullWidth/>
                            </Box>
                            <Box sx={{ my: 2 }}>
                                <TextField {...register('city')} label="City" variant="outlined" required fullWidth/>
                            </Box>
                            <Box sx={{display: 'flex', justifyContent: 'flex-end', marginTop: 2}}>
                                <Button type="submit" variant="contained" color="primary" disabled={loading}>
                                    Create Employee
                                </Button>
                            </Box>
                        </Grid>
                    </Grid>
                </form>
                <ToastContainer
                    position="top-right"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="light"
                />
            </Container>
        </Box>
    );
}

export default CreateEmployeePage;
