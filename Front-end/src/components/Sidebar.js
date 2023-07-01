import { useState, useContext } from 'react';
import { Drawer, List, ListItem, ListItemIcon, ListItemText, IconButton } from '@mui/material';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PeopleIcon from '@mui/icons-material/People';
import AssignmentIcon from '@mui/icons-material/Assignment';
import ComputerIcon from '@mui/icons-material/Computer';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import BusinessIcon from '@mui/icons-material/Business';
import { Link } from 'react-router-dom';
import UserContext from '../context/user-context';

function Sidebar() {
    const items = [
        { name: 'Dashboard', link: '/dashboard', icon: <DashboardIcon /> },
        { name: 'Create new employee', link: '/create-employee', icon: <PersonAddIcon /> },
        { name: 'All employees', link: '/all-employees', icon: <PeopleIcon /> },
        { name: 'Requirements', link: '/Requirements', icon: <AssignmentIcon /> },
        { name: 'Terminal', link: '/terminal', icon: <ComputerIcon /> },
        { name: 'Card Detector', link: '/card-detector', icon: <CreditCardIcon /> },

    ];

    const [open, setOpen] = useState(true);

    const { deleteUserCredentials } = useContext(UserContext);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const handleLogout = () => {
        deleteUserCredentials();
    };

    return (
        <Drawer variant="permanent" open={open}>
            <IconButton onClick={open ? handleDrawerClose : handleDrawerOpen}>
                {open ? <ChevronLeftIcon /> : <ChevronRightIcon />}
            </IconButton>
            <List>
                {items.map((item, index) => (
                    <ListItem button key={index} component={Link} to={item.link}>
                        <ListItemIcon>{item.icon}</ListItemIcon>
                        {open && <ListItemText primary={item.name} />}
                    </ListItem>
                ))}
                <ListItem button onClick={handleLogout}>
                    <ListItemIcon><ExitToAppIcon /></ListItemIcon>
                    {open && <ListItemText primary="Logout" />}
                </ListItem>
            </List>
        </Drawer>
    );
}

export default Sidebar;
