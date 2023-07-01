import React, {useContext, useEffect, useState} from 'react';
import Sidebar from '../../components/Sidebar';
import {
    Container,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton,
    Box,
    Modal,
    Button,
    TextField,
    Grid,
    Typography
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import {getEmployees, deleteUser} from "../../services/apiService";
import UserContext from '../../context/user-context';
import {updateUserProfile} from "../../services/apiService";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function AllEmployeesPage() {
    const [employees, setEmployees] = useState([]);
    const [open, setOpen] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const {businessId} = useContext(UserContext);


    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dateOfBirth, SetdateOfBirth] = useState('');
    const [gender, Setgender] = useState('');
    const [contactNumber, SetContactNumber] = useState('');
    const [country, SetCountry] = useState('');
    const [state, Setstate] = useState('');
    const [city, SetCity] = useState('');

    useEffect(() => {
        const fetchEmployees = async () => {
            try {
                const response = await getEmployees(businessId);
                setEmployees(response);
            } catch (error) {
                console.log(error);
            }
        }

        fetchEmployees();
    }, []);

    const handleOpen = (employee) => {
        setSelectedEmployee(employee);
        setOpen(true);
        setFirstName(employee.userProfileDTO.first_name);
        setLastName(employee.userProfileDTO.last_name);
        SetdateOfBirth(employee.userProfileDTO.date_of_birth);
        Setgender(employee.userProfileDTO.gender);
        SetContactNumber(employee.userProfileDTO.contact_number);
        SetCountry(employee.userProfileDTO.country);
        SetCity(employee.userProfileDTO.city);
        Setstate(employee.userProfileDTO.state);
    };


    const handleClose = () => {
        setOpen(false);
    };

    const handleSave = async () => {
        const updatedProfile = {
            first_name: firstName,
            last_name: lastName,
            date_of_birth: dateOfBirth,
            gender: gender,
            contact_number: contactNumber,
            country: country,
            state: state,
            city: city
        };

        try {
            await updateUserProfile(selectedEmployee.businessUserProfileDto.userId, updatedProfile);
            toast.success("User has been successfully updated");

            // Обновляем состояние `employees` без перезагрузки страницы
            const updatedEmployees = employees.map(employee =>
                employee.businessUserProfileDto.userId === selectedEmployee.businessUserProfileDto.userId
                    ? { ...employee, userProfileDTO: updatedProfile }
                    : employee
            );
            setEmployees(updatedEmployees);

        } catch (error) {
            toast.error("Error when updating the user");
        }

        setOpen(false);
    };

    const handleDelete = async (employeeId) => {
        if(window.confirm("Вы действительно хотите удалить этого пользователя?")) {
            try {
                // Предположим, у вас есть функция `deleteUser` в вашем `apiService`
                await deleteUser(employeeId, businessId);
                toast.success("User successfully deleted");

                // Удаляем пользователя из состояния `employees`
                const updatedEmployees = employees.filter(employee =>
                    employee.businessUserProfileDto.userId !== employeeId
                );
                setEmployees(updatedEmployees);

            } catch (error) {
                toast.error("Error when deleting a user");
            }
        }
    };

    const modalStyle = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        boxShadow: 24,
        p: 4,
    };

    return (
        <Box sx={{display: 'flex'}}>
            <Sidebar/>
            <Container sx={{marginTop: 3, marginLeft: '240px'}}>
                <TableContainer component={Paper}>
                    <Table sx={{minWidth: 650}} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>First Name</TableCell>
                                <TableCell>Last Name</TableCell>
                                <TableCell align="right">Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {employees.map((employee) => (
                                <TableRow key={employee.businessUserProfileDto.id}>
                                    <TableCell component="th" scope="row">
                                        {employee.userProfileDTO.first_name}
                                    </TableCell>
                                    <TableCell>{employee.userProfileDTO.last_name}</TableCell>
                                    <TableCell align="right">
                                        <IconButton aria-label="edit" onClick={() => handleOpen(employee)}>
                                            <EditIcon/>
                                        </IconButton>
                                        <IconButton aria-label="delete" onClick={() => handleDelete(employee.businessUserProfileDto.userId)}>
                                            <DeleteIcon/>
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <Modal
                    open={open}
                    onClose={handleClose}
                >
                    <Box sx={modalStyle}>
                        <Typography variant="h6" component="h2">
                            Редактирование профиля
                        </Typography>
                        {selectedEmployee && (
                            <Box component="form" sx={{my: 1}}>
                                <TextField label="First Name" value={firstName}
                                           onChange={e => setFirstName(e.target.value)} fullWidth margin="normal"/>
                                <TextField label="Last Name" value={lastName}
                                           onChange={e => setLastName(e.target.value)} fullWidth margin="normal"/>
                                <TextField label="Date of Birth" value={dateOfBirth}
                                           onChange={e => SetdateOfBirth(e.target.value)} fullWidth margin="normal"/>
                                <TextField label="Gender" value={gender} onChange={e => Setgender(e.target.value)}
                                           fullWidth margin="normal"/>
                                <TextField label="Contact Number" value={contactNumber}
                                           onChange={e => SetContactNumber(e.target.value)} fullWidth margin="normal"/>
                                <TextField label="Country" value={country} onChange={e => SetCountry(e.target.value)}
                                           fullWidth margin="normal"/>
                                <TextField label="State" value={state} onChange={e => Setstate(e.target.value)}
                                           fullWidth margin="normal"/>
                                <TextField label="City" value={city} onChange={e => SetCity(e.target.value)} fullWidth
                                           margin="normal"/>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 3 }}>
                                    <Button onClick={handleSave}>Сохранить</Button>
                                    <Button onClick={handleClose}>Отмена</Button>
                                </Box>
                            </Box>
                        )}
                    </Box>
                </Modal>
            </Container>
            <ToastContainer />
        </Box>
    );
}

export default AllEmployeesPage;
