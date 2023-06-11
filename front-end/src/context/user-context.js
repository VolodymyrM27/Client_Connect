import React, {useEffect, useState} from "react";

const UserContext = React.createContext({
    token: "",
    role: "",
    setUserCredentials: (userCredentials) => {},
    deleteUserCredentials: () => {}
});

export const UserContextProvider = ({children}) => {
    const [userData, setUserData] = useState({token: "", role: ""});

    useEffect(() => {
        let userData = JSON.parse(localStorage.getItem("userData"));
        if (userData) {
            setUserData({
                token: userData.token, role: userData.role,
            });
            console.log(userData.token)
        }
    }, []);

    const getTokenHandler = () => {
        if (userData.token){
            return userData.token
        }
        return JSON.parse(localStorage.getItem("userData"))?.token
    }

    const getRoleHandler = () => {
        if (userData.role){
            return userData.role
        }
        return JSON.parse(localStorage.getItem("userData"))?.role
    }

    const setUserCredentialsHandler = (userCredentials) => {
        localStorage.setItem("userData", JSON.stringify(userCredentials))
        setUserData(userCredentials)
    };

    const deleteUserCredentialsHandler = () => {
        localStorage.setItem("userData", JSON.stringify({token: "", role: ""}))
        setUserData({token: "", role: ""})
    };

    return (<UserContext.Provider
        value={{
            token: getTokenHandler(),
            role: getRoleHandler(),
            setUserCredentials: setUserCredentialsHandler,
            deleteUserCredentials: deleteUserCredentialsHandler
        }}
    >
        {children}
    </UserContext.Provider>);
};

export default UserContext;
