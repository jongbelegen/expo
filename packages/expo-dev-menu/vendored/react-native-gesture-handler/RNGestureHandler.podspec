Pod::Spec.new do |s|
    s.name         = "DevMenuRNGestureHandler"
    s.version      = "2.1.2"
    s.homepage     = "https://github.com/software-mansion/react-native-gesture-handler"
    s.license      = "MIT"
    s.platform       = :ios, '12.0'
    s.source       = { :git => "https://github.com/software-mansion/react-native-gesture-handler", :tag => "2.1.2" }
    s.source_files = "ios/**/*.{h,m}"
  
    s.dependency "React-Core"
end
