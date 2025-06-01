import React, { useEffect, useState } from 'react';
import PlacesAutocomplete from 'react-places-autocomplete';
import { Link } from 'react-router-dom';
import { Alert, Button, Col, Container, Form, Row, Card } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { endpoints,authApis } from '../configs/Api';
import cookie from 'react-cookies';

// const Home = () => {
//     const [jobpostings, setJobpostings] = useState([]);
//     const [categories, setCategories] = useState([]);
//     const [totalPages, setTotalPages] = useState(1);
//     const [currentPage, setCurrentPage] = useState(1);
//     const [formData, setFormData] = useState({
//         title: '',
//         categoryId: '',
//         location: '',
//         // latitude: '',
//         // longitude: '',
//         workingTime: '',
//         salaryFrom: '',
//         salaryTo: '',
//        // radius: '',
//     });
//     const [user, setUser] = useState(null);
//     const navigate = useNavigate();
//     const location = useLocation();

//     //   const { isLoaded } = useJsApiLoader({
//     //     googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
//     //     libraries: ['places'],
//     //   });

//     useEffect(() => {
//         // Lấy danh mục
//         axios.get(endpoints.categories).then((response) => {
//             setCategories(response.data);
//         });

//         // Lấy người dùng
//         const token = cookie.load('token');
//         if (token) {
//             authApis().get(endpoints.currentUser).then((response) => {
//                 setUser(response.data);
//             });
//         }

//         // Lấy tham số từ URL
//         const params = new URLSearchParams(location.search);
//         const page = params.get('page') || '1';
//         setCurrentPage(parseInt(page));
//         setFormData({
//             title: params.get('title') || '',
//             categoryId: params.get('categoryId') || '',
//             location: params.get('location') || '',
//             //   latitude: params.get('latitude') || '',
//             //   longitude: params.get('longitude') || '',
//             workingTime: params.get('workingTime') || '',
//             salaryFrom: params.get('salaryFrom') || '',
//             salaryTo: params.get('salaryTo') || '',
//             //radius: params.get('radius') || '',
//         });

//         // Lấy jobpostings
//         fetchJobs(params);// Truyền params vào fetchJobs
//     }, [location]);

//     const fetchJobs = (params) => {
//         const queryParams = {
//             kw: params.get('title') || '',
//             categoryId: params.get('categoryId') || '',
//             location: params.get('location') || '',
//             //   latitude: params.get('latitude') || '',
//             //   longitude: params.get('longitude') || '',
//             workingTime: params.get('workingTime') || '',
//             salaryFrom: params.get('salaryFrom') || '',
//             salaryTo: params.get('salaryTo') || '',
//             //radius: params.get('radius') || '',
//             page: params.get('page') || '1',
//         };

//         axios.get(endpoints.jobpostings, { params: queryParams }).then((response) => {
//             setJobpostings(response.data.jobs);
//             setTotalPages(response.data.totalPages);
//         });
//     };

//     const handleInputChange = (e) => {
//         const { name, value } = e.target;
//         setFormData({ ...formData, [name]: value });
//     };

//     const handleSubmit = (e) => {
//         e.preventDefault();
//         const params = new URLSearchParams();
//         Object.entries(formData).forEach(([key, value]) => {
//           if (value) params.append(key, value);
//         });
//         params.append('page', '1');
//         navigate(`/?${params.toString()}`);
//       };

//     const handleDelete = (id) => {
//         if (window.confirm('Xóa bài đăng này?')) {
//             authApis().delete(`${endpoints.deleteJob}/${id}`).then(() => {
//                 fetchJobs(new URLSearchParams(location.search));
//             });
//         }
//     };

//     const handlePageChange = (page) => {
//         const params = new URLSearchParams(location.search);
//         params.set('page', page);
//         navigate(`/?${params.toString()}`);
//     };

//     //   const initAutocomplete = () => {
//     //     if (isLoaded && autocompleteRef.current) {
//     //       const autocomplete = new google.maps.places.Autocomplete(autocompleteRef.current);
//     //       autocomplete.addListener('place_changed', () => {
//     //         const place = autocomplete.getPlace();
//     //         if (place.geometry) {
//     //           setFormData({
//     //             ...formData,
//     //             location: place.formatted_address,
//     //             latitude: place.geometry.location.lat(),
//     //             longitude: place.geometry.location.lng(),
//     //           });
//     //         }
//     //       });
//     //     }
//     //   };

//     //   if (!isLoaded) return <div>Loading...</div>;

//     return (
//         <Container className="my-4">
//           <Form onSubmit={handleSubmit}>
//             <Row className="g-3 align-items-end">
//               <Col md={4}>
//                 <Form.Control
//                   type="text"
//                   name="title"
//                   value={formData.title}
//                   onChange={(e) => handleInputChange(e.target.value)}
//                   placeholder="Từ khóa..."
//                 />
//               </Col>
//               {/* <Col md={4}>
//                 <Form.Select name="categoryId" value={formData.categoryId} onChange={(e) => handleInputChange(e.target.value)}>
//                   <option value="">Tất cả ngành nghề</option>
//                   {categories.map((c) => (
//                     <option key={c.id} value={c.id}>{c.name}</option>
//                   ))}
//                 </Form.Select>
//               </Col> */}
//               <Col md={4}>
//                 <PlacesAutocomplete
//                   value={formData.location}
//                   onChange={handleInputChange}
//                   onSelect={handleInputChange}
//                 >
//                   {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
//                     <div>
//                       <Form.Control
//                         {...getInputProps({
//                           placeholder: 'Địa điểm...',
//                           className: 'location-search-input',
//                         })}
//                       />
//                       <div className="autocomplete-dropdown-container">
//                         {loading && <div>Loading...</div>}
//                         {suggestions.map((suggestion) => (
//                           <div
//                             {...getSuggestionItemProps(suggestion)}
//                             key={suggestion.placeId}
//                           >
//                             {suggestion.description}
//                           </div>
//                         ))}
//                       </div>
//                     </div>
//                   )}
//                 </PlacesAutocomplete>
//               </Col>
//               <Col md={3}>
//                 <Form.Select name="workingTime" value={formData.workingTime} onChange={(e) => handleInputChange(e.target.value)}>
//                   <option value="">Tất cả thời gian</option>
//                   <option value="Toàn thời gian">Toàn thời gian</option>
//                   <option value="Bán thời gian">Bán thời gian</option>
//                   <option value="Remote">Remote</option>
//                 </Form.Select>
//               </Col>
//               <Col md={3}>
//                 <Form.Control
//                   type="number"
//                   name="salaryFrom"
//                   value={formData.salaryFrom}
//                   onChange={(e) => handleInputChange(e.target.value)}
//                   placeholder="Lương tối thiểu (VNĐ)"
//                 />
//               </Col>
//               <Col md={3}>
//                 <Form.Control
//                   type="number"
//                   name="salaryTo"
//                   value={formData.salaryTo}
//                   onChange={(e) => handleInputChange(e.target.value)}
//                   placeholder="Lương tối đa (VNĐ)"
//                 />
//               </Col>
//               <Col md={3}>
//                 <Form.Control
//                   type="number"
//                   name="radius"
//                   value={formData.radius}
//                   onChange={(e) => handleInputChange(e.target.value)}
//                   placeholder="Bán kính (km)"
//                 />
//               </Col>
//               <Col md={12}>
//                 <Button type="submit" variant="primary">Tìm kiếm</Button>
//               </Col>
//             </Row>
//           </Form>

//           <hr />

//           {user && user.role === 'EMPLOYER' && (
//             <Button as={Link} to="/jobpostings" variant="success" className="mb-3">
//               Đăng bài
//             </Button>
//           )}

//           {jobpostings.length === 0 ? (
//             <Alert variant="info">Không tìm thấy công việc.</Alert>
//           ) : (
//             <>
//               <Row xs={1} md={2} lg={3} className="g-4 mb-4">
//                 {jobpostings.map((job) => (
//                   <Col key={job.id}>
//                     <Card className="h-100 shadow-sm">
//                       <Card.Img
//                         variant="top"
//                         src={job.company.user?.avatar || 'https://console.cloudinary.com/app/c-e85a8930706ea6b97e7ca7edffd7c1/assets/media_library/homepage/asset/051dbceaa7f4a87d8840f54bbc1bd8d3/manage/summary?context=manage'}
//                         style={{ height: '100px', objectFit: 'contain' }}
//                       />
//                       <Card.Body>
//                         <Card.Title>{job.title}</Card.Title>
//                         <Card.Text>
//                           <strong>Mức lương:</strong> {job.salaryFrom} - {job.salaryTo} VNĐ<br />
//                           <strong>Địa điểm:</strong> {job.location}<br />
//                           <strong>Thời gian:</strong> {job.workingTime}
//                         </Card.Text>
//                         <Button variant="outline-primary" size="sm" className="me-2">
//                           Ứng tuyển
//                         </Button>
//                         <Button variant="outline-success" size="sm">
//                           <i className="bi bi-heart"></i>
//                         </Button>
//                       </Card.Body>
//                       <Card.Footer className="text-muted">
//                         {user && user.role === 'EMPLOYER' && (
//                           <>
//                             <Button
//                               variant="danger"
//                               size="sm"
//                               onClick={() => handleDelete(job.id)}
//                               className="me-2"
//                             >
//                               Xóa
//                             </Button>
//                             <Button
//                               variant="info"
//                               size="sm"
//                               as={Link}
//                               to={`/jobpostings/${job.id}`}
//                             >
//                               Sửa
//                             </Button>
//                           </>
//                         )}
//                       </Card.Footer>
//                     </Card>
//                   </Col>
//                 ))}
//               </Row>

//               <nav aria-label="Page navigation">
//                 <ul className="pagination justify-content-center">
//                   <li className={`page-item ${currentPage === 1 ? 'disabled' : ''}`}>
//                     <Button
//                       className="page-link"
//                       onClick={() => handlePageChange(currentPage - 1)}
//                     >
//                       Trước
//                     </Button>
//                   </li>
//                   {[...Array(totalPages).keys()].map((i) => (
//                     <li
//                       key={i + 1}
//                       className={`page-item ${currentPage === i + 1 ? 'active' : ''}`}
//                     >
//                       <Button
//                         className="page-link"
//                         onClick={() => handlePageChange(i + 1)}
//                       >
//                         {i + 1}
//                       </Button>
//                     </li>
//                   ))}
//                   <li className={`page-item ${currentPage === totalPages ? 'disabled' : ''}`}>
//                     <Button
//                       className="page-link"
//                       onClick={() => handlePageChange(currentPage + 1)}
//                     >
//                       Sau
//                     </Button>
//                   </li>
//                 </ul>
//               </nav>
//             </>
//           )}
//         </Container>
//       );
// };

// export default Home;
const Home = () => {
  return (
    <>
    </>
  );
}
export default Home;