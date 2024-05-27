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
                <td>{user.firstName} {user.lastName}</td>
                <td>København</td>
                <td>CPHBusiness</td>
                <td>
                    <input type="checkbox" checked/>
                </td>
            </tr>
            </tbody>
        </table>

        </>
     );
}

export default userInfo;