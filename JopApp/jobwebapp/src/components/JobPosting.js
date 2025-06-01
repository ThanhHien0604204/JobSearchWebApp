import React from "react";
// const JobCard = ({ job }) => {
//     return (
//         <div className="bg-white shadow-md rounded-lg p-4 flex flex-col space-y-2 hover:shadow-lg transition-shadow">
//             <div className="flex items-center space-x-3">
//                 <img src={job.company_logo} alt="Company Logo" className="w-12 h-12 rounded" />
//                 <div>
//                     <h3 className="text-lg font-semibold">{job.title}</h3>
//                     <p className="text-sm text-gray-600">{job.company_name}</p>
//                 </div>
//             </div>
//             <p className="text-sm text-gray-500">{job.description}</p>
//             <p className="text-sm text-gray-700">Lương: {job.salary}</p>
//             <p className="text-sm text-gray-700">Địa điểm: {job.location}</p>
//             <p className="text-sm text-gray-700">Thời gian: {job.working_time}</p>
//             <p className="text-sm text-blue-600">{job.category_name}</p>
//             <button className="mt-2 flex items-center text-green-600 hover:text-green-800">
//                 <svg className="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
//                     <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4.318 6.318a4.5 4.5 0 016.364 0L12 7.636l1.318-1.318a4.5 4.5 0 016.364 6.364L12 20.364l-7.682-7.682a4.5 4.5 0 010-6.364z" />
//                 </svg>
//                 Yêu thích
//             </button>
//         </div>
//     );
// };
const JobPosting = () => {
//const JobPosting = ({ onFilterChange }) => {
    // const FilterBar = ({ onFilterChange }) => {
    //     const [keyword, setKeyword] = React.useState("");
    //     const [location, setLocation] = React.useState("");
    //     const [category, setCategory] = React.useState("");
    //     const [salary, setSalary] = React.useState("");

    //     const handleFilter = () => {
    //         onFilterChange({ keyword, location, category, salary });
    //     };

    //     return (
    //         <div className="bg-teal-900 p-2 flex items-center space-x-2 text-white rounded-lg shadow-md">
    //             <button className="p-2 rounded-md hover:bg-teal-800 focus:outline-none">
    //                 <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    //                     <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7" />
    //                 </svg>
    //                 <span className="ml-1">Danh mục Nghề</span>
    //             </button>
    //             <input 
    //                 type="text"
    //                 value={keyword}
    //                 onChange={(e) => setKeyword(e.target.value)}
    //                 placeholder="Vị trí tuyển dụng, tên công ty"
    //                 className="p-2 rounded-md flex-1 text-gray-800 focus:outline-none"
    //             />
    //             <select
    //                 value={location}
    //                 onChange={(e) => setLocation(e.target.value)}
    //                 className="p-2 rounded-md text-gray-800 bg-white focus:outline-none"
    //             >
    //                 <option value="">Địa điểm</option>
    //                 {mockLocations.map((loc) => (
    //                     <option key={loc} value={loc}>{loc}</option>
    //                 ))}
    //             </select>
    //             <button
    //                 onClick={handleFilter}
    //                 className="bg-green-600 text-white px-4 py-2 rounded-full hover:bg-green-700 flex items-center"
    //             >
    //                 <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    //                     <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
    //                 </svg>
    //                 Tìm kiếm
    //             </button>
    //         </div>
    //     );
    // }
    return (
        <>
            <h1>DANH SACH JOB</h1>
        </>
    );
}
    export default JobPosting;