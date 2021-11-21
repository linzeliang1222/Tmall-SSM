package top.linzeliang.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.linzeliang.tmall.mapper.PropertyValueMapper;
import top.linzeliang.tmall.pojo.Product;
import top.linzeliang.tmall.pojo.Property;
import top.linzeliang.tmall.pojo.PropertyValue;
import top.linzeliang.tmall.pojo.PropertyValueExample;
import top.linzeliang.tmall.service.PropertyService;
import top.linzeliang.tmall.service.PropertyValueService;

import java.util.List;

@Service("propertyValueServiceImpl")
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    @Qualifier("propertyValueMapper")
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    @Qualifier("propertyServiceImpl")
    private PropertyService propertyService;

    @Override
    public void init(Product p) {
        List<Property> pts = propertyService.list(p.getCid());
        for (Property pt : pts) {
            PropertyValue pv = get(pt.getId(), p.getId());
            if (null == pv) {
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria()
                .andPtidEqualTo(ptid)
                .andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty()) {
            return null;
        }
        return pvs.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : pvs) {
            Property property = propertyService.get(pv.getPid());
            pv.setProperty(property);
        }

        return pvs;
    }
}
