function userInfo(user) {

    // This component will display the user's information
 


    return ( 
        <>
        <table>
            <thead>
                
            <tr>
                <td>Name: </td>
                <td>City: </td>
                <td>Education institution:  </td>
                <td>Verified: </td>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{user.name}</td>
                <td>{user.city}</td>
                <td>{user.educationInstitution}</td>
                <td>
                    <checkbox>{user.verified}</checkbox>
                </td>
            </tr>
            </tbody>
        </table>

        </>
     );
}

export default userInfo;