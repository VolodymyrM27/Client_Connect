import React, {useEffect, useState} from "react";
import { setAuthToken, clearAuthToken,getBusinessByUser } from '../services/apiService';

const UserContext = React.createContext({
    token: "",
    role: "",
    businessId: "",
    setUserCredentials: (userCredentials) => {},
    deleteUserCredentials: () => {},
    getBusinessId: () => {},
    setBusinessId: (id) => {},
});

export const UserContextProvider = ({children}) => {
    const [userData, setUserData] = useState({token: "", role: "", businessId: "" });

    useEffect(() => {
        let storedUserData = JSON.parse(localStorage.getItem("userData"));
        if (storedUserData && storedUserData.token) {
            setUserData(storedUserData);
            setAuthToken(storedUserData.token);
        }
    }, []);

    const getTokenHandler = () => userData.token || JSON.parse(localStorage.getItem("userData"))?.token;

    const getRoleHandler = () => userData.role || JSON.parse(localStorage.getItem("userData"))?.role;

    const getBusinessIdHandler = () => userData.businessId || JSON.parse(localStorage.getItem("businessData"))?.businessId;

    const setBusinessIdHandler = (id) => {
        localStorage.setItem("businessData", JSON.stringify({businessId: id}));
        setUserData(prevData => ({...prevData, businessId: id}));
    };

    const setUserCredentialsHandler = async (userCredentials) => {
        console.log(userCredentials);
        const { id, token, role } = userCredentials;
        setAuthToken(token);

        const businessInfo = await getBusinessByUser(id);
        setBusinessIdHandler(businessInfo.id);

        localStorage.setItem("userData", JSON.stringify({token, role, businessId: businessInfo.id}));
        setUserData({token, role, businessId: businessInfo.id});
    };

    const deleteUserCredentialsHandler = () => {
        localStorage.removeItem("userData");
        localStorage.removeItem("businessData");
        setUserData({token: "", role: "", businessId: ""});
        clearAuthToken();
    };

    return (
        <UserContext.Provider
            value={{
                token: getTokenHandler(),
                role: getRoleHandler(),
                businessId: getBusinessIdHandler(),
                setUserCredentials: setUserCredentialsHandler,
                deleteUserCredentials: deleteUserCredentialsHandler
            }}
        >
            {children}
        </UserContext.Provider>
    );
};

export default UserContext;
